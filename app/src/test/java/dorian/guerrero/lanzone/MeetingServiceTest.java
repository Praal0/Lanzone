package dorian.guerrero.lanzone;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.DummyGenerator;
import dorian.guerrero.lanzone.service.MeetingApiService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingServiceTest {

    private MeetingApiService mService;
    private List<Meeting> lstMeeting;

    @Before
    public void setup() {
        mService = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Meeting> neighbours = mService.getMeeting();
        List<Meeting> expectedNeighbours = DummyGenerator.DUMMY_MEETINGS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Meeting meetingToDelete = mService.getMeeting().get(0);
        mService.deleteMeeting(meetingToDelete);
        assertFalse(mService.getMeeting().contains(meetingToDelete));
    }

    @Test
    public void AddNeighbourWithSuccess() {
        mService.getMeeting().clear();
        Meeting neighbourToAdd = DummyGenerator.DUMMY_MEETINGS.get(0);
        mService.createMeeting(neighbourToAdd);
        assertTrue(mService.getMeeting().contains(neighbourToAdd));
    }




}