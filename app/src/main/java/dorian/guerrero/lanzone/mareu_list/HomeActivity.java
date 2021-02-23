package dorian.guerrero.lanzone.mareu_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.events.DeleteMeetingEvent;
import dorian.guerrero.lanzone.service.MeetingApiService;

public class HomeActivity extends AppCompatActivity {

    private MeetingApiService mMeetingApiService;
    private FloatingActionButton addButton;
    private RecyclerView mRecyclerView;
    private String s1[],s2[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mRecyclerView = findViewById(R.id.rcvMeeting);

        s1 = getResources().getStringArray(R.array.progamming_language);
        s2 = getResources().getStringArray(R.array.email);

        addButton = findViewById(R.id.addMeeting);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent addIntent = new Intent(HomeActivity.this, AddReuActivity.class);
                startActivity(addIntent);
            }
        });

        MeetingAdpater meetingAdpater = new MeetingAdpater(this,s1,s2);
        mRecyclerView.setAdapter(meetingAdpater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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
    public void onDeleteFavorisNeighbour(DeleteMeetingEvent event) {
        mMeetingApiService.deleteMeeting(event.meeting);
    }

}


