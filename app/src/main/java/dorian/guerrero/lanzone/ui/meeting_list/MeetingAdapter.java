package dorian.guerrero.lanzone.ui.meeting_list;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.events.DeleteMeetingEvent;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;
import dorian.guerrero.lanzone.service.MeetingApiService;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> {
    List<Meeting> mMeeting;
    List<Room> mRooms;


    public MeetingAdapter(List<Meeting> items) {
        mMeeting = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MeetingApiService mApiService = DI.getMeetingApiService();
        Meeting meeting = mMeeting.get(position);
        String nameRoom = mApiService.getNameRoom(meeting.getRoomId());
        Room room = mApiService.getRoomWithId(meeting.getRoomId());
        String img = room.getDrawableRes();

        Glide.with(holder.myImage.getContext())
                .load(img)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.myImage);

        holder.myTextDescription.setText( meeting.meetingSubject + " - " + nameRoom);
        holder.myTextEmail.setText(TextUtils.join(", ",
                meeting.getMeetingGuestList()));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeeting.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myTextDescription, myTextEmail;
        ImageView myImage;
        ImageButton mDeleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextDescription = itemView.findViewById(R.id.description_item);
            myTextEmail = itemView.findViewById(R.id.participants_item);
            myImage = itemView.findViewById(R.id.circle_item);
            mDeleteButton = itemView.findViewById(R.id.delete_item);

        }
    }
}
