package dorian.guerrero.lanzone.ui.Fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.service.MeetingApiService;
import dorian.guerrero.lanzone.ui.AddMeetingActivity;

public class FilterDialogFragment extends DialogFragment {

    @BindView(R.id.date_filter) TextInputEditText mDateFilter;
    @BindView(R.id.room_filter) AutoCompleteTextView mRoomFilter;

    private MeetingApiService mMeetingApiService;
    private List<String> mRooms;
    private DateTime mDate;
    private Long mRoomId;

    private OnButtonClickedListener mCallback;

    public FilterDialogFragment(List<String> rooms) {
        mRooms = rooms;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        mMeetingApiService = DI.getMeetingApiService();
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.fragment_filter, null);
        ButterKnife.bind(this, view);

        mRoomFilter.setAdapter(new ArrayAdapter<>(
                Objects.requireNonNull(getContext()),
                R.layout.room_item,
                mRooms));

        builder.setView(view);
        builder.setTitle(R.string.select_filter);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRoomId = mMeetingApiService.getIdRoom(mRoomFilter.getText().toString());
                mMeetingApiService.getMeetings(mDate,mRoomId);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

        builder.setNeutralButton(R.string.reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO trouver un moyen de mettre la liste full dans ma liste
            }
        });

        return builder.create();
    }


    public interface OnButtonClickedListener {
        void onButtonClicked(Calendar date, String room, boolean reset);
    }

    private void createCallbackToParentActivity() {
    }

    @OnClick(R.id.date_filter)
    void displayDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDatePickerDialog;

        mDatePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()),android.R.style.Theme_Holo_Light,
                (view, year, month, dayOfMonth) -> {
                month++;
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                    String date =  dayOfMonth + "/" + month + "/" + year;
                    mDateFilter.setText(date);
                    mDate = new DateTime(year,month,dayOfMonth,0,0,0);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDatePickerDialog.show();
    }

    @OnTouch(R.id.room_filter)
    boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            mRoomFilter.showDropDown();
            return true;
        }

        return (event.getAction() == MotionEvent.ACTION_UP);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();

    }
}
