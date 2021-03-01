package dorian.guerrero.lanzone.service;

import java.util.ArrayList;
import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;

public class DummyMeetingApiService implements MeetingApiService{
    private List<Meeting> mMeetings;
    private List<Room> mRooms;

    public DummyMeetingApiService(){
        mMeetings = DummyGenerator.generateMeetings();
        mRooms = GeneratorRoom.generateRoom();
    }

    /**
     * Return List Meeting
     */
    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    @Override
    public List<Room> getRooms() {
        return mRooms;
    }

    @Override
    public List<String> getListNameRooms() {
        List<String>mRoom = new ArrayList<>();
        for (Room room : getRooms()){
            mRoom.add(room.getRoomName());
        }
        return mRoom;
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
