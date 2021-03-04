package dorian.guerrero.lanzone.model;


import org.joda.time.DateTime;

import java.util.List;
import java.util.Objects;

public class Meeting {

    public long idMeeting;

    public long room;

    public String meetingSubject;

    public DateTime meetingBegin;

    public DateTime meetingEnd;

    private List<String> mParticipants;

    public Meeting(long idMeeting, long room, String meetingSubject, DateTime meetingTimeDebut, DateTime meetingTimeFin, List<String> mParticipants) {
        this.idMeeting = idMeeting;
        this.room = room;
        this.meetingSubject = meetingSubject;
        this.meetingBegin = meetingTimeDebut;
        this.meetingEnd = meetingTimeFin;
        this.mParticipants =  mParticipants;
    }

    //Getters

    public long getIdMeeting() {
        return idMeeting;
    }

    public long getRoomId() {
        return room;
    }

    public String getMeetingSubject() { return meetingSubject; }


    public DateTime getMeetingHeureBegin() { return meetingBegin;
    }

    public DateTime getMeetingHeureEnd() {
        return meetingEnd;
    }

    public List<String>  getMeetingGuestList() {
        return mParticipants;
    }

    //Setters

    public void setIdMeeting(long id){this.idMeeting = id;}

    public void setRoom(Integer room){this.room = room;}

    public void setMeetingSubject(String meetingSubject){this.meetingSubject = meetingSubject;}

    public void setMeetingBegin(DateTime heureDebut){
        this.meetingBegin = heureDebut;
    }

    public void setMeetingEnd(DateTime heureFin){
        this.meetingEnd = heureFin;
    }

    public void setMeetingGuestList(List<String> meetingGuestList){
        this.mParticipants = meetingGuestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(idMeeting, meeting.idMeeting);
    }

}