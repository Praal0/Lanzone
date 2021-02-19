package dorian.guerrero.lanzone.service;

import java.util.ArrayList;
import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;

public class DummyMeetingApiService implements MeetingApiService{
    private List<Meeting> lstMeeting = new ArrayList<>();

    /**
     * {@inheritDoc}
     */

    public List<Meeting> getNeighbours() {
        return lstMeeting;
    }

    /**
     * Create a new Meeting in list
     * @param meeting
     */
    public void createNeighbour(Meeting meeting) {
        lstMeeting.add(meeting);
    }

    /**
     * Remove Meeting in list
     */
    public void deleteNeighbour(Meeting meeting) {
        lstMeeting.remove(meeting);

    }




}
