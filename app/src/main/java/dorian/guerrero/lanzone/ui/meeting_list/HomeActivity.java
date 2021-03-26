package dorian.guerrero.lanzone.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.events.AddMeetingEvent;
import dorian.guerrero.lanzone.events.DeleteMeetingEvent;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.MeetingApiService;
import dorian.guerrero.lanzone.ui.AddMeetingActivity;
import dorian.guerrero.lanzone.ui.Fragment.FilterDialogFragment;

public class HomeActivity extends AppCompatActivity implements FilterDialogFragment.OnButtonClickedListener  {
    public static MeetingApiService sApiService;
    List<Meeting> mMeetings;
    @BindView(R.id.addMeeting) FloatingActionButton mFloatingActionButton;
    @BindView(R.id.rcvMeeting) RecyclerView mRecyclerView;
    private MeetingAdapter meetingAdapater;
    private DateTime dateSelect;
    private long roomSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Bind ButterKnif and init all element (Adapter,List...)
        ButterKnife.bind(this);
        sApiService = DI.getMeetingApiService();
        mMeetings = new ArrayList<>();
        meetingAdapater = new MeetingAdapter(sApiService.getMeeting());
        mRecyclerView.setAdapter(meetingAdapater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdd();

    }

    private void initAdd() {
        mFloatingActionButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AddMeetingActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meeting_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter :
                performFilterDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performFilterDialog() {
        // Init Filter
        FilterDialogFragment filterDialog = new FilterDialogFragment(sApiService.getListNameRooms());
        filterDialog.show(getSupportFragmentManager(), "filter");
    }


    @Override
    public void onResume() {
        super.onResume();
        initList(null,null);
    }


    private void initList(DateTime date, Long room){
        // Init List use when we use filter or delete and other
        mMeetings = sApiService.getMeetingFilter(date,room);
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

    public void onButtonClicked(DateTime date, Long room, boolean reset) {
        // When we click on reset or Validation in alert dialog we lauch filter
        if (reset || date != null || room != null ){
            roomSelect = room;
            dateSelect = date;
            initList(date, room);
        }
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        sApiService.deleteMeeting(event.meeting);
        initList(dateSelect,roomSelect);

    }

    @Subscribe
    public void onCreateMeeting(AddMeetingEvent event){
        sApiService.createMeeting(event.meeting);
    }

}