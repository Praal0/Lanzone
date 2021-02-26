package dorian.guerrero.lanzone.service;

import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;

public interface MeetingApiService {

    List<Room> getRooms();


    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Meeting> getMeeting();

    /**
     * Deletes a neighbour
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a neighbour
     * @param meeting
     */
    void createMeeting(Meeting meeting);
}
