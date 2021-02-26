package dorian.guerrero.lanzone.events;

import dorian.guerrero.lanzone.model.Meeting;

public class DeleteMeetingEvent {
    /**
     * Event fired when a user delete a Meeting
     */

    public Meeting meeting;

    /**
     * Constructor
     * @param meeting
     */

    public DeleteMeetingEvent(Meeting meeting){this.meeting = meeting;};
}
