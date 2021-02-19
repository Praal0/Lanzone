package dorian.guerrero.lanzone.events;

import dorian.guerrero.lanzone.model.Meeting;

/**
 * Event fired when a user add a Meeting
 */

public class AddMeetingEvent {
    public Meeting meeting;

    /**
     * Constructor
     * @param meeting
     */

    public AddMeetingEvent(Meeting meeting){this.meeting = meeting;};
}
