package dorian.guerrero.lanzone.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.events.AddMeetingEvent;
import dorian.guerrero.lanzone.events.DeleteMeetingEvent;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.MeetingApiService;
import dorian.guerrero.lanzone.ui.AddMeetingActivity;

public class HomeActivity extends AppCompatActivity  {

    private MeetingApiService mMeetingApiService;
    List<Meeting> mMeetings,mMeetingFull;
    private FloatingActionButton addButton;
    private RecyclerView mRecyclerView;
    private MeetingAdapter meetingAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetings = new ArrayList<>();
        mMeetingFull = new ArrayList<>(mMeetings);
        mMeetingApiService = DI.getMeetingApiService();
        setContentView(R.layout.activity_home);
        mRecyclerView = findViewById(R.id.rcvMeeting);
        meetingAdapater = new MeetingAdapter(mMeetingApiService.getMeeting());
        mRecyclerView.setAdapter(meetingAdapater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addbutton();
    }

    private void addbutton() {
        addButton = findViewById(R.id.addMeeting);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent addIntent = new Intent(HomeActivity.this, AddMeetingActivity.class);
                startActivity(addIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meeting_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                meetingAdapater.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            performFilter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void performFilter() {

    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void initList() {
        mMeetings = mMeetingApiService.getMeeting();
        meetingAdapater = new MeetingAdapter(mMeetings);
        mRecyclerView.setAdapter(meetingAdapater);
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
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mMeetingApiService.deleteMeeting(event.meeting);
        initList();
    }

    @Subscribe
    public void onCreateMeeting(AddMeetingEvent event){
        mMeetingApiService.createMeeting(event.meeting);
    }

}


