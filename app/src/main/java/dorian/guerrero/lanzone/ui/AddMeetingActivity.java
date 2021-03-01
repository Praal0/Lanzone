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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.model.Room;
import dorian.guerrero.lanzone.service.MeetingApiService;


public class AddMeetingActivity extends AppCompatActivity {
    Date dt;
    List<String> roomNameList;
    private MeetingApiService mApiService;
    private TextView mEdtDate,mEditTextDe, mEditTexteA;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private AutoCompleteTextView mRoomNameAutoCompleteTextView;
    private ChipGroup mEmailsChipGroup;
    private TextInputEditText mEmailsTextInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reu);
        mApiService = DI.getMeetingApiService();
        initDate();
        initTime();
        initListRoom();
        initEmailsOnKeyListener();

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
        dt = new Date();
        mEditTextDe = findViewById(R.id.from);
        mEditTextDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mEditTextDe.setText(hourOfDay+":"+minutes);

                    }
                },
                        dt.getHours(), dt.getMinutes(), true);
                timePickerDialog.show();

            }
        });

        mEditTexteA = findViewById(R.id.to);
        mEditTexteA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mEditTexteA.setText(hourOfDay+":"+minutes);
                    }
                }, dt.getHours(), dt.getMinutes(), true);
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
                String date =  dayOfMonth + "/" + month + "/" + year;
                mEdtDate.setText(date);
            }
        };
    }
}