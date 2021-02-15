package dorian.guerrero.lanzone.model;


import java.util.Calendar;
import java.util.List;

public class Meeting {

    private long id;
    private String mRoomName;
    private Calendar mStart;
    private Calendar mEnd;
    private String mTopic;
    private List<String> mParticipants;

    /**
     * Constructor
     * @param topic topic of the meeting
     * @param roomName name of the meeting room
     * @param start meeting start date and time
     * @param end meeting end date and time
     * @param participants list of email addresses of meeting participants
     */
    public Meeting(long id,String roomName, Calendar start, Calendar end, String topic, List<String> participants) {
        this.id = id;
        this.mRoomName = roomName;
        this.mStart = start;
        this.mEnd = end;
        this.mTopic = topic;
        this.mParticipants = participants;
    }

    public long getId() {
        return id;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public Calendar getStart() {
        return mStart;
    }

    public Calendar getEnd() {
        return mEnd;
    }

    public String getTopic() {
        return mTopic;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }
}
