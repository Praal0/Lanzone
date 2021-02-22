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
     * Return List Meeting
     */
    @Override
    public List<Meeting> getMeeting() {
        return lstMeeting;
    }

    /**
     * Delete Meeting in list
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        lstMeeting.remove(meeting);
    }

    /**
     * Create a new Meeting in list
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        lstMeeting.add(meeting);

    }
}
