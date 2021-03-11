package dorian.guerrero.lanzone.service;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;

public interface MeetingApiService {


    /**
     * Get all my Meeting
     * @return {@link List}
     */
    List<Meeting> getMeeting();

    /**
     * Get all my Rooms
     * @return {@link List}
     */
    List<Room> getRooms();

    /**
     * Get all Rooms name
     * @return {@link List}
     */
    List<String>getListNameRooms();

    /**
     * Get all Id of Room
     * @return {@link List}
     */
    long getIdRoom(String roomName);

    String getNameRoom(long idRoom);

    /**
     * Deletes a Meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a Meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Get all Data of Room with Id
     * @return {@link Room}
     * @param id
     */
    Room getRoomWithId(long id);


    /**
     * Get meetings
     *
     * Unfiltered, filtered by date, filtered by room name or filtered by date and room name
     * @return list of meetings
     */
    List<Meeting> getMeetings(DateTime date, Long roomId);



}
