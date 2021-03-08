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

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> implements Filterable {
    List<Meeting> mMeeting,mMeetingFull;


    public MeetingAdapter(List<Meeting> items) {
        mMeeting = items;
        // We save full list for when we use search view
        mMeetingFull = new ArrayList<>(mMeeting);

    }

    @Override


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_detail, parent, false);
        ButterKnife.bind(this,view);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                mMeetingFull.remove(meeting);
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

    @Override
    public Filter getFilter() {
        return meetingFilter;
    }

    private Filter meetingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Meeting> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mMeetingFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Meeting item : mMeeting) {
                    if (item.getMeetingSubject().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    if (item.getMeetingGuestList().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMeeting.clear();
            mMeeting.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



}
