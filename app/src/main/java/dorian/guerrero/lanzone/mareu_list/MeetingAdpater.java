package dorian.guerrero.lanzone.mareu_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.events.DeleteMeetingEvent;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.MeetingApiService;

public class MeetingAdpater extends RecyclerView.Adapter<MeetingAdpater.MyViewHolder> {
    List<Meeting> mMeeting;
    Context mContext;
    MeetingApiService mApiService;

    public MeetingAdpater(List<Meeting> items) {
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
        Meeting meeting = mMeeting.get(position);
        holder.myTextDescription.setText(meeting.getMeetingSubject());
        holder.myTextEmail.setText("toto@gmail.com");

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
