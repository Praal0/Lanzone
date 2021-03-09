package dorian.guerrero.lanzone.service;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import dorian.guerrero.lanzone.di.DI;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;


public class DummyMeetingApiService implements MeetingApiService{
    private List<Meeting> mMeetings;
    private List<Room> mRooms;
    private MeetingApiService mMeetingApiService;

    public DummyMeetingApiService(){
        mMeetings = DummyGenerator.generateMeetings();
        mRooms = GeneratorRoom.generateRoom();
        mMeetingApiService = DI.getMeetingApiService();
    }

    /**
     * Return List Meeting
     */
    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    /**
     * Return List Rooms
     */
    @Override
    public List<Room> getRooms() {
        return mRooms;
    }

    /**
     * Return List Name of Rooms
     */
    @Override
    public List<String> getListNameRooms() {
        List<String>mRoom = new ArrayList<>();
        for (Room room : getRooms()){
            mRoom.add(room.getRoomName());
        }
        return mRoom;
    }

    /**
     * Return id of Rooms
     */
    @Override
    public long getIdRoom(String roomName) {
        long idRoom = 0;
       for (Room room : getRooms()){
           String roomNames = room.getRoomName();
           if (roomNames.equals(roomName)){
               idRoom = room.getId();
               break;
           }
       }
        return idRoom;
    }

    /**
     * Return name of Rooms
     */
    @Override
    public String getNameRoom(long idRoom) {
        String name = null;
        for (Room room : getRooms()){
            long roomId = room.getId();
            if (roomId == idRoom){
                name = room.getRoomName();
                break;
            }
        }
        return name;
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

    @Override
    public Room getRoomWithId(long id) {
        Room roomReturn = null;
        for (Room room : getRooms()){
            long roomId = room.getId();
            if (roomId == id){
                roomReturn = room;
                break;
            }
        }
        return roomReturn;
    }

    @Override
    public List<Meeting> getMeetings(DateTime date, String roomName) {
        if (date != null && roomName != null && ! roomName.isEmpty())
            return getMeetingsMatchDate(date, getMeetingsMatchRoomName(roomName, mMeetings));
        else if (date != null)
            return getMeetingsMatchDate(date, mMeetings);
        else if (roomName != null && ! roomName.isEmpty())
            return getMeetingsMatchRoomName(roomName, mMeetings);
        return mMeetings;
    }

    private List<Meeting> getMeetingsMatchDate(DateTime date, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();
        for (Meeting m :meetings){
            if (m.getMeetingHeureBegin().equals(date)){
                tmp.add(m);
            }
        }
        return tmp;

    }

    private List<Meeting> getMeetingsMatchRoomName(String roomName, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();
        Long idRoom = mMeetingApiService.getIdRoom(roomName);

        for (Meeting m: meetings)
            if (m.getRoomId() == idRoom)
                tmp.add(m);
        return tmp;
    }
}
