package dorian.guerrero.lanzone.service;

import org.joda.time.DateTime;

import java.text.Normalizer;
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

    /**
     * Get Room use with Id of Room
     * @param id
     */
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

    /**
     * Check if meeting exist or not before we can add in list
     * @param meeting
     */
    @Override
    public Boolean checkMeeting(Meeting meeting) {
        Boolean valid = true;
        for(Meeting m : getMeeting()){
            if (meeting.getRoomId() == m.getRoomId()) {
                if (meeting.getMeetingHeureBegin().equals(m.getMeetingHeureBegin()) || meeting.getMeetingHeureEnd().equals(m.getMeetingHeureEnd()))
                    return false;
                if (meeting.getMeetingHeureBegin().isAfter(m.getMeetingHeureBegin()) && meeting.getMeetingHeureBegin().isBefore(m.getMeetingHeureEnd()))
                    return false;
                if (meeting.getMeetingHeureEnd().isAfter(m.getMeetingHeureBegin()) && meeting.getMeetingHeureEnd().isBefore(m.getMeetingHeureEnd()))
                    return false;
            }
        }
        return valid;
    }

    /**
     * Return List Meeting with element who use Room or in date
     * @param date,roomId
     */
    @Override
    public List<Meeting> getMeetingFilter(DateTime date, Long roomId) {
        if (date != null && roomId != null && roomId != 0)
            return getMeetingsMatchDate(date, getMeetingsMatchRoomName(roomId, mMeetings));
        else if (date != null)
            return getMeetingsMatchDate(date, mMeetings);
        else if (roomId != null && roomId != 0)
            return getMeetingsMatchRoomName(roomId, mMeetings);
        return mMeetings;
    }

    /**
     * Return List Meeting with element in date
     * @param date,roomId
     */
    private List<Meeting> getMeetingsMatchDate(DateTime date, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();
        for (Meeting m :meetings){
            // We Remove clock for check date
            DateTime dateTime = new DateTime(m.getMeetingHeureBegin().getYear(),m.getMeetingHeureBegin().getMonthOfYear()
                    ,m.getMeetingHeureBegin().getDayOfMonth(),0,0,0);
            if (dateTime.equals(date)){
                tmp.add(m);
            }
        }
        return tmp;

    }


    /**
     * Return List Meeting with element in Room selected
     * @param roomId,meetings
     */
    private List<Meeting> getMeetingsMatchRoomName(Long roomId, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();
        for (Meeting m: meetings){
            if (m.room == roomId)
                tmp.add(m);
        }
        return tmp;
    }
}
