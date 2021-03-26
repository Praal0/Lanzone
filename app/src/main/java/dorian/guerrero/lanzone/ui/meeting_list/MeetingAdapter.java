package dorian.guerrero.lanzone.ui.meeting_list;

import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.events.DeleteMeetingEvent;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;
import dorian.guerrero.lanzone.service.MeetingApiService;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> {
    List<Meeting> mMeeting;

    public MeetingAdapter(List<Meeting> items) {
        mMeeting = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_detail, parent, false);
        ButterKnife.bind(this,view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MeetingApiService mApiService = DI.getMeetingApiService();
        Meeting meeting = mMeeting.get(position);

        //We take all element of Room with ID
        Room room = mApiService.getRoomWithId(meeting.getRoomId());

        //Change Pater for have time : HH h mm
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH'h'mm");
        String str = fmt.print(meeting.meetingBegin);

        holder.myImage.setImageResource(room.getDrawableRes());

        holder.myTextDescription.setText(meeting.meetingSubject + " - " + str + " - " + room.getRoomName());

        // We Insert element in description delimiter with ,
        holder.myTextEmail.setText(TextUtils.join(", ",
                meeting.getMeetingGuestList()));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
                notifyItemRemoved(position);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myTextDescription, myTextEmail;
        ImageView myImage;
        ImageButton mDeleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // We use findview because we cannot use butterknif here
            myTextDescription = itemView.findViewById(R.id.description_item);
            myTextEmail = itemView.findViewById(R.id.participants_item);
            myImage = itemView.findViewById(R.id.circle_item);
            mDeleteButton = itemView.findViewById(R.id.delete_item);
        }
    }

    @Override
    public int getItemCount() {
        return mMeeting.size() ;
    }

}
