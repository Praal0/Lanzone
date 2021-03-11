package dorian.guerrero.lanzone.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.events.AddMeetingEvent;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.MeetingApiService;


public class AddMeetingActivity extends AppCompatActivity {
    private boolean mError;
    private List<String> participants;
    private DateTime dtBegin, dtEnd,dateReu;
    private List<String> roomNameList;
    private MeetingApiService mApiService;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @BindView(R.id.date) TextView mDateTextView;
    @BindView(R.id.from) TextView  mEditTextTimeStart;
    @BindView(R.id.to) EditText mEditTexteTimeEnd;
    @BindView(R.id.room_name) AutoCompleteTextView mRoomNameAutoCompleteTextView;
    @BindView(R.id.emails_group) ChipGroup mEmailsChipGroup;
    @BindView(R.id.emails) TextInputEditText mEmailsTextInputEditText;
    @BindView(R.id.btn_add) Button mButtonAdd;
    @BindView(R.id.room_name_layout) TextInputLayout mRoomNameTextInputLayout;
    @BindView(R.id.to_layout)TextInputLayout mEndTimeTextInputLayout;
    @BindView(R.id.participants)TextInputLayout mEmailsTextInputLayout;
    @BindView(R.id.topic_layout) TextInputLayout mSujetInputLayout;
    @BindView(R.id.date_layout) TextInputLayout mDateTextInputLayout;
    @BindView(R.id.from_layout) TextInputLayout mStartTimeTextInputLayout;

    private int lastSelectedHourDe = -1;
    private int lastSelectedMinuteDe = -1;
    private int lastSelectedHourA = -1;
    private int lastSelectedMinuteA = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reu);
        mApiService = DI.getMeetingApiService();
        ButterKnife.bind(this);
        initDate();
        initTime();
        initListRoom();
        initEmailsOnKeyListener();
        initButtonAdd();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initButtonAdd() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                participants = validateEmailInput(mEmailsTextInputLayout, mEmailsChipGroup);
                validateTextView(mStartTimeTextInputLayout);
                validateTextView(mEndTimeTextInputLayout);
                String subjetMeeting = validateTextView(mSujetInputLayout);
                validateDateTextView();
                long idRoom = mApiService.getIdRoom(validateTextView(mRoomNameTextInputLayout));
                // If error we send message to user can complete Meeting
                if (mError){
                    Snackbar.make(v,R.string.error_field , Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    mError = false;
                } else {
                    Meeting meeting = new Meeting(System.currentTimeMillis(), idRoom ,subjetMeeting, dtBegin, dtEnd,participants);
                    EventBus.getDefault().post(new AddMeetingEvent(meeting));
                    finish();
                }
            }
        });
    }

    private void validateDateTextView(){
        String dateMeeting = mDateTextView.getText().toString();
        if (dateMeeting.isEmpty()){
            mError = true;
            mDateTextInputLayout.setError(getText(R.string.error_empty_field));
        } else {
            mDateTextInputLayout.setError(null);
        }
    }

    private List<String> validateEmailInput(TextInputLayout inputValue, ChipGroup emails) {
        int nb = emails.getChildCount();
        List<String> lEmails = new ArrayList<>();

        if (nb == 0){
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        }else{
            inputValue.setError(null);
        }

        for (int i = 0; i<nb;i++){
            Chip tmpEmail = (Chip) emails.getChildAt(i);
            String email = tmpEmail.getText().toString();
            lEmails.add(email);
        }
        return lEmails;
    }

    private String validateTextView (TextInputLayout inputValue){
      String  tmpValue = inputValue.getEditText().getText().toString();
       if (tmpValue.isEmpty()) {
           inputValue.setError(getText(R.string.error_empty_field));
           mError = true;
           return null;
       }else {
           inputValue.setError(null);
           return tmpValue;
       }
    }

    private void initEmailsOnKeyListener() {
        mEmailsTextInputEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN){
              if (keyCode == KeyEvent.KEYCODE_ENTER){
                  String value = Objects.requireNonNull(mEmailsTextInputEditText.getText()).toString().trim();
                  if (validateEmailAdress(value)){
                      addEmailToChipGroup(value);
                      mEmailsTextInputLayout.setError(null);
                      return true;
                  }else{
                      mEmailsTextInputLayout.setError(getText(R.string.error_invalid_mail));
                      return false;
                  }
              }
            }
            return false;
        });
    }

    private boolean validateEmailAdress(String value) {
        String email = value;
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            return false;
        }
    }

    private void addEmailToChipGroup(String email) {
        final Chip emailChip = new Chip(AddMeetingActivity.this);
        emailChip.setText(email);
        emailChip.setCloseIconVisible(true);
        emailChip.setOnCloseIconClickListener(v -> mEmailsChipGroup.removeView(emailChip));

        mEmailsChipGroup.addView(emailChip);
        mEmailsTextInputEditText.setText("");
    }

    private void initListRoom() {
        // Meeting room -->
        roomNameList = mApiService.getListNameRooms();

        mRoomNameAutoCompleteTextView.setText(R.string.select_filter);

        mRoomNameAutoCompleteTextView.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.room_item,
                roomNameList));

        mRoomNameAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    mRoomNameAutoCompleteTextView.showDropDown();
                    return true;
                }
                return (event.getAction() == MotionEvent.ACTION_UP);
            }
        });
    }

    private void initTime() {
        if(this.lastSelectedHourDe == -1)  {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHourDe = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinuteDe = c.get(Calendar.MINUTE);
        }
        mEditTextTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDateTextView.getText().toString().isEmpty()){
                    mStartTimeTextInputLayout.setError(getText(R.string.error_empty_date));
                }else{
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                            lastSelectedHourDe = hourOfDay;
                            lastSelectedMinuteDe = minutes;
                            dtBegin = new DateTime(dateReu.getYear(), dateReu.getMonthOfYear(), dateReu.getDayOfMonth(), hourOfDay, minutes, 0, 0);
                            if (dtEnd != null){
                                if (dtBegin.isBefore(dtEnd)){
                                    mEditTextTimeStart.setText(String.format("%02d:%02d", hourOfDay, minutes));
                                    mStartTimeTextInputLayout.setError(null);
                                }else{
                                    dtBegin = null;
                                    mEditTextTimeStart.setText("");
                                    mStartTimeTextInputLayout.setError(getText(R.string.error_invalid_time_after));
                                }
                            }else{
                                mEditTextTimeStart.setText(String.format("%02d:%02d", hourOfDay, minutes));
                            }
                        }
                    }, lastSelectedHourDe, lastSelectedMinuteDe, true);
                    timePickerDialog.show();
                }
            }
        });

        if(this.lastSelectedHourA == -1)  {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHourA = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinuteA = c.get(Calendar.MINUTE);
        }
        mEditTexteTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDateTextView.getText().toString().isEmpty()){
                    mEndTimeTextInputLayout.setError(getText(R.string.error_empty_date));
                }else{
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                            lastSelectedHourA = hourOfDay;
                            lastSelectedMinuteA = minutes;
                            dtEnd = new DateTime(dateReu.getYear(), dateReu.getMonthOfYear(), dateReu.getDayOfMonth(), hourOfDay, minutes, 0, 0);
                            if (dtEnd.isAfter(dtBegin)){
                                mEditTexteTimeEnd.setText(String.format("%02d:%02d", hourOfDay, minutes));
                                mEndTimeTextInputLayout.setError(null);
                            }else{
                                dtEnd = null;
                                mEditTexteTimeEnd.setText("");
                                mEndTimeTextInputLayout.setError(getText(R.string.error_invalid_time_after));
                            }
                        }
                    }, lastSelectedHourA, lastSelectedMinuteA, true);
                    timePickerDialog.show();
                }
            }
        });
    }

    private void initDate() {
        mDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddMeetingActivity.this, android.R.style.Theme_Holo_Light,
                        mDateSetListener,year,month,day);

                dialog.updateDate(year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                dateReu = new DateTime(year,month,dayOfMonth,0,0,0);
                String date =  dayOfMonth + "/" + month + "/" + year;
                mDateTextView.setText(date);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCreateMeeting(AddMeetingEvent event){
        mApiService.createMeeting(event.meeting);
    }
}