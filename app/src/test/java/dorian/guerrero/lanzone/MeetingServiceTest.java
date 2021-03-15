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

public class MeetingServiceTest {

    private MeetingApiService mService;
    private Meeting mMeetingNotValide,mMeetingValide,mMeetingPast;
    private List<Meeting> mMeetingList;
    private Boolean valide;
    private DateTime mDateTime;


    @Before
    public void setup() {
        mService = DI.getNewInstanceApiService();
        mMeetingNotValide = new Meeting(System.currentTimeMillis(),1,"Reunion Valide",
                new DateTime(2021, 3, 15, 12, 30, 0, 0),
                new DateTime(2021, 3, 15, 13, 0, 0, 0),
                asList("toto@hotmail.fr","tito@gmail.com"));
        mMeetingValide = new Meeting(System.currentTimeMillis(),1,"Reunion Non Valide",
                new DateTime(2021, 3, 15, 14, 30, 0, 0),
                new DateTime(2021, 3, 15, 15, 0, 0, 0),
                asList("toto@hotmail.fr","tito@gmail.com"));

        mMeetingPast = new Meeting(System.currentTimeMillis(),1,"Reunion Past",
                new DateTime(2020, 3, 15, 14, 30, 0, 0),
                new DateTime(2020, 3, 15, 15, 0, 0, 0),
                asList("toto@hotmail.fr","tito@gmail.com"));
        mDateTime = new DateTime(2021,3,15,0,0);
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
        Meeting meetingToAdd = DummyGenerator.DUMMY_MEETINGS.get(0);
        mService.createMeeting(meetingToAdd);
        assertTrue(mService.getMeeting().contains(meetingToAdd));
    }

    @Test
    public void checkMeetingWithSuccess() {
        valide = mService.checkMeeting(mMeetingNotValide);
        assertEquals(valide == false,false);
        valide = mService.checkMeeting(mMeetingValide);
        assertTrue(valide == true);
    }

    @Test
    public void getMeetingFilterWithSuccess(){
        //We add 3 Meeting in list
        mService.createMeeting(mMeetingValide);
        mService.createMeeting(mMeetingNotValide);
        mService.createMeeting(mMeetingPast);

        // Now we filter to check if we have 3 élement (my 2 element and one in list who use same Date)
        mMeetingList = mService.getMeetingFilter(mDateTime,null);
        assertTrue(mMeetingList.size() == 3);

        //We check  and if mMeetingValide is in List
        assertTrue(mService.getMeetingFilter(mDateTime,null).contains(mMeetingValide));

        // We CHack if we have only 3 Meeting (3 Who we have add in list)
        mMeetingList = mService.getMeetingFilter(null,1L);
        assertTrue(mMeetingList.size() == 3);


        //Now we filter with IdRoom and check if we have only my 2 element
        mMeetingList = mService.getMeetingFilter(null,1L);
        assertTrue(mService.getMeetingFilter(mDateTime,null).contains(mMeetingValide));
        assertTrue(mMeetingList.size() == 3);

        //Now we filter with date and list
        assertTrue(mService.getMeetingFilter(mDateTime,1L).contains(mMeetingValide));
        assertTrue(mService.getMeetingFilter(mDateTime,1L).size()==2);

        // We check if we don't have element use other Room
        Meeting meeting = DummyGenerator.DUMMY_MEETINGS.get(2);
        assertFalse(mService.getMeetingFilter(mDateTime,1L).contains(meeting));
        assertFalse(mService.getMeetingFilter(mDateTime,null).contains(meeting));
        assertFalse(mService.getMeetingFilter(null,1L).contains(meeting));
    }

}