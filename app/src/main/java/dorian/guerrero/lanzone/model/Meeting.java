package dorian.guerrero.lanzone.model;


import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Meeting {

    public long idMeeting;

    public long room;

    public String meetingSubject;

    public Date meetingDate;

    public String meetingHeureDebut;

    public String meetingHeureFin;

    private List<String> mParticipants;

    public Meeting(long idMeeting, long room, String meetingSubject,Date meetingDate, String meetingTimeDebut, String meetingTimeFin, List<String> mParticipants) {
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

    public String getMeetingHeureDebut() {
        return meetingHeureDebut;
    }

    public String getMeetingHeureFin() {
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

    public void setMeetingHeureDebut(String heureDebut){
        this.meetingHeureDebut = heureDebut;
    }

    public void setMeetingHeureFin(String heureFin){
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