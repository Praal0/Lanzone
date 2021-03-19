package dorian.guerrero.lanzone;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Collection;

import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.MeetingApiService;
import dorian.guerrero.lanzone.ui.AddMeetingActivity;
import dorian.guerrero.lanzone.ui.meeting_list.HomeActivity;
import dorian.guerrero.lanzone.utils.assertions.DeleteViewAction;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static dorian.guerrero.lanzone.service.DummyGeneratorMeeting.generateMeetings;
import static dorian.guerrero.lanzone.service.DummyGeneratorMeeting.generateMeetingsTest;
import static dorian.guerrero.lanzone.utils.assertions.ChipValueAssertion.matchesChipTextAtPosition;
import static dorian.guerrero.lanzone.utils.assertions.RecyclerViewItemCountAssertion.withItemCount;
import static dorian.guerrero.lanzone.utils.assertions.TextInputLayoutErrorValueAssertion.matchesErrorText;
import static dorian.guerrero.lanzone.utils.assertions.TextInputLayoutNoErrorValueAssertion.matchesNoErrorText;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
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
    private static int ITEMS_ONE = 1;
    private static int ITEMS_COUNT = 3;
    public static final String INVALID_EMAIL = "foobar";
    public static final String VALID_EMAIL_1 = "d.gue@gmail.com";
    public static final String VALID_EMAIL_2 = "toto@gmail.com";
    private HomeActivity mHomeActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<HomeActivity>(HomeActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                        DI.initializeMeetingApiService(generateMeetingsTest());
                }
            };
    @Rule
    public ActivityTestRule<AddMeetingActivity> mAddMeetingActivityRule =
            new ActivityTestRule<>(AddMeetingActivity.class);

    @Before
    public void setUp() {
        mHomeActivity = mActivityRule.getActivity();
        assertThat(mHomeActivity, notNullValue());
    }
    @Test

    /**
     * Check if list not Empty
     */
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.rcvMeeting))
                .check(matches(hasMinimumChildCount(ITEMS_ONE)));
    }

    /**
     * Check the correct entries of the Emails field (press Enter key)
     */
    @Test
    public void givenTwoValidEmail_whenTypeTextWithDelimiter_thenGetEmailsWithoutError() {
        // Initialize test -->
        onView(withId(R.id.addMeeting)).perform(click());
        // add chip
        onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1));

        onView(withId(R.id.emails)).perform(pressImeActionButton());

        // confirm that first chip is present
        onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, VALID_EMAIL_1));

        // add chip email 2
        onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_2));

        onView(withId(R.id.emails)).perform(pressImeActionButton());

        // confirm that second chip is present
        onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(2, VALID_EMAIL_2));

        // Test -->
        onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
        onView(withId(R.id.participants)).check(matchesNoErrorText());
        // Test <--
    }

    /**
     * Check invalid entry of the Emails field (press Enter key)
     */
    @Test
    public void givenInvalidEmail_whenTypeTextWithDelimiter_thenGetErrorMessage() {
        // Initialize test -->
        onView(withId(R.id.addMeeting)).perform(click());
        // add chip
        onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1));
        onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        // confirm that the first chip is present
        onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, VALID_EMAIL_1));
        // Initialize test <--

        // Test -->
        onView(withId(R.id.emails)).perform(typeText(INVALID_EMAIL));
        onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.participants))
                .check(matchesErrorText("Le mail est incorrect"));
        onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
        // Test <--
    }

    /**
     * Scenario: abort an booked in progress
     */
    @Test
    public void whenMakingReservationAndWhenWeClickToCancel_thenItIsAborted() {
        // Click to add meeting
        onView(ViewMatchers.withId(R.id.addMeeting))
                .perform(click());

        Activity currentAddMeetingActivity = getActivityInstance();

        // Abort
        Espresso.pressBack();

        // Check abort
        assertTrue(currentAddMeetingActivity.isFinishing());
    }

    private Activity currentActivity = null;

    private Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities =
                    ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            for (Activity activity: resumedActivities){
                currentActivity = activity;
                break;
            }
        });

        return currentActivity;
    }

    /**
     * Check room filter
     */
    @Test
    public void check_room_filter() {
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.room_filter))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Luigi")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_ONE));
    }
    /**
     * Check room filter and we test reset
     */
    @Test
    public void check_room_filter_and_reset() {
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.room_filter))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Mario")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(android.R.id.button1))
                .perform(click());

        // We check if the list is empty

        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_EMPTY));

        // After we Reset the list
        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(android.R.id.button3))
                .perform(click());

        // We Check if we have all element in List
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_COUNT));
    }

    /**
     * Check date filter
     */
    @Test
    public void check_date_filter() {
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.date_filter))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2021,3,15));

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_ONE));
    }


    /**
     * Check date filter and we test reset
     */
    @Test
    public void check_date_filter_and_reset() {
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.date_filter))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2021,3,15));

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(1));

        // After we Reset the list
        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(android.R.id.button3))
                .perform(click());

        // We Check if we have all element in List
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_COUNT));
    }

    /**
     * Check date and room filter
     */
    @Test
    public void check_date_and_room_filter() {
        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_COUNT));

        // Room
        onView(ViewMatchers.withId(R.id.filter))
                .perform(click());

        onView(withId(R.id.room_filter))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Pikachu")))
                .inRoot(isPlatformPopup())
                .perform(click());

        // Date
        onView(withId(R.id.date_filter))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2021,3,21));
        onView(withId(android.R.id.button1)).perform(click());

        // Valid
        onView(withId(android.R.id.button1)).perform(click());

        onView(ViewMatchers.withId(R.id.rcvMeeting))
                .check(withItemCount(ITEMS_ONE));
    }


}