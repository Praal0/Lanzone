package dorian.guerrero.lanzone;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;
import dorian.guerrero.lanzone.service.DummyGenerator;
import dorian.guerrero.lanzone.service.GeneratorRoom;
import dorian.guerrero.lanzone.service.MeetingApiService;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingServiceTest {

    private MeetingApiService mService;
    private List<Room> lstRoom;
    private Room mRoom;
    private Meeting mMeetingNotValide,mMeetingValide;
    private List<Meeting> lstMeeting;
    private Boolean valide;

    @Before
    public void setup() {
        mService = DI.getNewInstanceApiService();
        mMeetingNotValide = new Meeting(System.currentTimeMillis(),2,"Reunion 1",
                new DateTime(2021, 3, 15, 12, 30, 0, 0),
                new DateTime(2021, 3, 15, 13, 0, 0, 0),
                asList("toto@hotmail.fr","tito@gmail.com"));
        mMeetingValide = new Meeting(System.currentTimeMillis(),2,"Reunion 1",
                new DateTime(2021, 3, 15, 14, 30, 0, 0),
                new DateTime(2021, 3, 15, 15, 0, 0, 0),
                asList("toto@hotmail.fr","tito@gmail.com"));
        lstRoom = GeneratorRoom.DUMMY_ROOM;
        lstMeeting = DummyGenerator.DUMMY_MEETINGS;
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

    @Test
    public void checkMeetingWithSuccess() {
        valide = mService.checkMeeting(mMeetingNotValide);
        assertTrue(valide == false);
        valide = mService.checkMeeting(mMeetingValide);
        assertTrue(valide == true);
    }

    @Test
    public void getMeetingFilterWithSucess(){

    }

    @Test
    public void getMeetingsMatchDate(){

    }

    @Test
    public void getMeetingsMatchRoomName(){

    }
}