package dorian.guerrero.lanzone.model;


import java.util.List;

public class Meeting {

    public long idMeeting;

    public long room;

    public String meetingSubject;

    public String meetingHeureDebut;

    public String meetingHeureFin;

    private List<String> mParticipants;

    public Meeting(long idMeeting, long room, String meetingSubject, String meetingTimeDebut, String meetingTimeFin, List<String> mParticipants) {
        this.idMeeting = idMeeting;
        this.room = room;
        this.meetingSubject = meetingSubject;
        this.meetingHeureDebut = meetingTimeDebut;
        this.meetingHeureFin = meetingTimeFin;
        this.mParticipants =  mParticipants;
    }

    //Getters

    public long getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(long id){this.idMeeting = id;}

    public long getRoom() {
        return room;
    }

    public void setRoom(Integer room){this.room = room;}

    public String getMeetingSubject() { return meetingSubject; }

    public void setMeetingSubject(String meetingSubject){this.meetingSubject = meetingSubject;}

    public String getMeetingHeureDebut() {
        return meetingHeureDebut;
    }



    public String getMeetingHeureFin() {
        return meetingHeureFin;
    }

    public List<String>  getMeetingGuestList() {
        return mParticipants;
    }

}