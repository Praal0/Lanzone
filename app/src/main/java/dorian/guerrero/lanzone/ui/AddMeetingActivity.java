package dorian.guerrero.lanzone.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
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

import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.events.AddMeetingEvent;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.MeetingApiService;


public class AddMeetingActivity extends AppCompatActivity {
    Date dt;
    Date dateReu;
    List<String> participants;
    private DateTime dtDebut,dtFin;
    List<String> roomNameList;
    private MeetingApiService mApiService;
    private TextView mEdtDate, mEditTextTimeStart, mEditTexteTimeEnd, mRootName, mTextSubjet;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private AutoCompleteTextView mRoomNameAutoCompleteTextView;
    private ChipGroup mEmailsChipGroup;
    private TextInputEditText mEmailsTextInputEditText;
    private Button mButtonAdd;
    private TextInputLayout mEmailsTextInputLayout;
    private int lastSelectedHourDe = -1;
    private int lastSelectedMinuteDe = -1;
    private int lastSelectedHourA = -1;
    private int lastSelectedMinuteA = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reu);
        mApiService = DI.getMeetingApiService();
        initDate();
        initTime();
        initListRoom();
        initEmailsOnKeyListener();
        initButtonAdd();
    }

    private void initButtonAdd() {
        mRootName = findViewById(R.id.room_name);
        mButtonAdd = findViewById(R.id.btn_add);
        mTextSubjet = findViewById(R.id.topic);
        mEmailsTextInputLayout = findViewById(R.id.participants);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                participants = validateEmailInput(mEmailsTextInputLayout, mEmailsChipGroup);
                long idRoom = mApiService.getIdRoom(mRootName.getText().toString());
                String txtSubject = mTextSubjet.getText().toString();
                Meeting meeting = new Meeting(System.currentTimeMillis(), idRoom ,txtSubject,dtDebut,dtFin,participants);
                EventBus.getDefault().post(new AddMeetingEvent(meeting));
                finish();
            }
        });
    }

    private List<String> validateEmailInput(TextInputLayout inputValue, ChipGroup emails) {
        int nb = emails.getChildCount();
        List<String> lEmails = new ArrayList<>();

        for (int i = 0; i<nb;i++){
            Chip tmpEmail = (Chip) emails.getChildAt(i);
            String email = tmpEmail.getText().toString();
            lEmails.add(email);
        }
        return lEmails;
    }

    private void initEmailsOnKeyListener() {
        mEmailsTextInputEditText = findViewById(R.id.emails);
        mEmailsChipGroup = findViewById(R.id.emails_group);
        mEmailsTextInputEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN){
              if (keyCode == KeyEvent.KEYCODE_ENTER){
                  String value = Objects.requireNonNull(mEmailsTextInputEditText.getText()).toString().trim();
                  addEmailToChipGroup(value);
                  return true;
              }
            }
            return false;
        });
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
        mRoomNameAutoCompleteTextView = findViewById(R.id.room_name);
        // Meeting room -->
        roomNameList = mApiService.getListNameRooms();


        mRoomNameAutoCompleteTextView.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.root_item,
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
        mEditTextTimeStart = findViewById(R.id.from);
        mEditTextTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        lastSelectedHourDe = hourOfDay;
                        lastSelectedMinuteDe = minutes;
                        mEditTextTimeStart.setText(String.format("%02d:%02d", hourOfDay, minutes));
                        dtDebut = new DateTime(dateReu.getYear(), dateReu.getMonth(), dateReu.getDay(), hourOfDay, minutes, 0, 0);
                    }
                }, lastSelectedHourDe, lastSelectedMinuteDe, true);
                timePickerDialog.show();
            }
        });
        if(this.lastSelectedHourA == -1)  {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHourA = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinuteA = c.get(Calendar.MINUTE);
        }
        mEditTexteTimeEnd = findViewById(R.id.to);
        mEditTexteTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        lastSelectedHourA = hourOfDay;
                        lastSelectedMinuteA = minutes;
                        mEditTexteTimeEnd.setText(String.format("%02d:%02d", hourOfDay, minutes));
                        dtFin = new DateTime(dateReu.getDate(), dateReu.getMonth(), dateReu.getDay(), hourOfDay, minutes, 0, 0);
                    }
                }, lastSelectedHourA, lastSelectedMinuteA, true);
                timePickerDialog.show();
            }
        });
    }

    private void initDate() {
        mEdtDate = findViewById(R.id.date);
        mEdtDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                dateReu = new Date(year,month,dayOfMonth);
                String date =  dayOfMonth + "/" + month + "/" + year;
                mEdtDate.setText(date);
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