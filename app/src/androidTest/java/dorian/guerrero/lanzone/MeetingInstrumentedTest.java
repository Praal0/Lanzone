package dorian.guerrero.lanzone;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

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
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
    private static int ITEMS_COUNT = 3;
    public static final String INVALID_EMAIL = "foobar";
    public static final String VALID_EMAIL_1 = "d.gue@gmail.com";
    public static final String VALID_EMAIL_2 = "toto@gmail.com";
    private MeetingApiService mApiService;
    private Context mContext;
    private HomeActivity mHomeActivity;
    private Meeting mMeeting;
    public String internalFieldDelimiter;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule(HomeActivity.class);
    @Rule
    public ActivityTestRule<AddMeetingActivity> mAddMeetingActivityRule =
            new ActivityTestRule<>(AddMeetingActivity.class);

    @Before
    public void setUp() {
        mHomeActivity = mActivityRule.getActivity();
        assertThat(mHomeActivity, notNullValue());
        mApiService = DI.getMeetingApiService();
        mMeeting =  new Meeting(1,2,"Reunion 1",
                new DateTime(2021, 3, 15, 12, 0, 0, 0),
                new DateTime(2021, 3, 15, 13, 0, 0, 0),
                asList("toto@hotmail.fr","tito@gmail.com"));
    }
    @Test

    /**
     * Check if list not Empty
     */
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.rcvMeeting))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Check Delete
     */

    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.rcvMeeting)).check(withItemCount(ITEMS_COUNT));

        // When perform a click on a delete icon
        onView(withId(R.id.rcvMeeting))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then : the number of element is 2
        onView(withId(R.id.rcvMeeting)).check(withItemCount(ITEMS_COUNT-1));
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
        // Initialize test <--

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
                .check(withItemCount(1));
    }









}










