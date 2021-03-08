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

import java.util.Calendar;
import java.util.List;
import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.ui.AddMeetingActivity;

public class FilterDialogFragment extends DialogFragment {
    @BindView(R.id.date_filter) TextInputEditText mDateFilter;
    @BindView(R.id.room_filter) AutoCompleteTextView mRoomFilter;

    private List<String> mRooms;
    private Calendar mDate;
    private String mRoom;

    private OnButtonClickedListener mCallback;

    public FilterDialogFragment(List<String> rooms) {
        mRooms = rooms;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

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

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            mCallback.onButtonClicked(mDate, mRoomFilter.getEditableText().toString(), false);
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

        builder.setNeutralButton(R.string.reset, (dialog, which) -> mCallback.onButtonClicked(mDate, mRoomFilter.getEditableText().toString(), true));

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
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, dayOfMonth);
                    mDateFilter.setText(DateFormat.getDateFormat(getContext()).format(cal.getTime()));
                    mDate = cal;
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

    }
}
