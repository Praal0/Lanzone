package dorian.guerrero.lanzone;

import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.MeetingApiService;
import dorian.guerrero.lanzone.ui.AddMeetingActivity;
import dorian.guerrero.lanzone.ui.meeting_list.HomeActivity;
import dorian.guerrero.lanzone.utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static dorian.guerrero.lanzone.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {

    private static int ITEMS_EMPTY = 0;
    private static int ITEMS_COUNT = 3;
    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    private Context mContext;
    private HomeActivity mHomeActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule(HomeActivity.class);
    public ActivityTestRule<AddMeetingActivity> mInfoNeighbourActivityTestRule =
            new ActivityTestRule(AddMeetingActivity.class);

    @Before
    public void setUp() {
        mHomeActivity = mActivityRule.getActivity();
        assertThat(mHomeActivity, notNullValue());
        mApiService = DI.getMeetingApiService();
    }
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.rcvMeeting)).check(withItemCount(ITEMS_COUNT));

        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.rcvMeeting)).check(withItemCount(ITEMS_COUNT-1));
    }

    
}