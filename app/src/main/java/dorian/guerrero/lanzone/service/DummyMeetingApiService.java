package dorian.guerrero.lanzone.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;

public class DummyMeetingApiService implements MeetingApiService{
    private List<Meeting> mMeetings;
    private final List<String> mRooms;

    public DummyMeetingApiService(){
        mMeetings = new ArrayList<>();
        mRooms = new ArrayList<>(Arrays.asList(
                "Room 1", "Room 2", "Room 3", "Room 4", "Room 5",
                "Room 6", "Room 7", "Room 8", "Room 9", "Room 10"));
    }
    /**
     * Return List of meeting
     */
    @Override
    public List<String> getRooms() {
        return mRooms;
    }

    /**
     * Return List Meeting
     */
    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    /**
     * Delete Meeting in list
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    /**
     * Create a new Meeting in list
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);

    }
}
