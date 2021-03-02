package dorian.guerrero.lanzone.model;


import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Meeting {

    public long idMeeting;

    public long room;

    public String meetingSubject;

    public Date meetingDate;

    public DateTime meetingHeureDebut;

    public DateTime meetingHeureFin;

    private List<String> mParticipants;

    public Meeting(long idMeeting, long room, String meetingSubject,Date meetingDate, DateTime meetingTimeDebut, DateTime meetingTimeFin, List<String> mParticipants) {
        this.idMeeting = idMeeting;
        this.room = room;
        this.meetingSubject = meetingSubject;
        this.meetingDate = meetingDate;
        this.meetingHeureDebut = meetingTimeDebut;
        this.meetingHeureFin = meetingTimeFin;
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

    public Date getMeetingDate(){ return meetingDate; }

    public DateTime getMeetingHeureDebut() { return meetingHeureDebut;
    }

    public DateTime getMeetingHeureFin() {
        return meetingHeureFin;
    }

    public List<String>  getMeetingGuestList() {
        return mParticipants;
    }

    //Setters

    public void setIdMeeting(long id){this.idMeeting = id;}

    public void setRoom(Integer room){this.room = room;}

    public void setMeetingSubject(String meetingSubject){this.meetingSubject = meetingSubject;}


    public void setMeetingDate(Date meetingDate){
        this.meetingDate = meetingDate;
    }

    public void setMeetingHeureDebut(DateTime heureDebut){
        this.meetingHeureDebut = heureDebut;
    }

    public void setMeetingHeureFin(DateTime heureFin){
        this.meetingHeureFin = heureFin;
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