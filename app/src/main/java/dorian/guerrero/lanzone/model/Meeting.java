package dorian.guerrero.lanzone.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

public class Meeting {

    private static List<Meeting> DUMMY_MEETINGS = asList(
            new Meeting(
                    1,
                    "Room 1",
                    "Objet Réunion 1",
                    new Date(1623247200000L),
                    new Date(1623250800000L),
                    Arrays.asList("tata@hotmail.fr")
            ),
            new Meeting(
                    2,
                    "Room 1",
                    "Objet Réunion 2",
                    new Date(1623247200000L),
                    new Date(1623250800000L),
                    Arrays.asList("toto@hotmail.fr")
            ),
            new Meeting(
                    3,
                    "Room 10",
                    "Objet Réunion 3",
                    new Date(1623247200000L),
                    new Date(1623250800000L),
                    Arrays.asList("titi@hotmail.fr")
            ),
            new Meeting(
                    4,
                    "Room 8",
                    "Objet Réunion 4",
                    new Date(1623247200000L),
                    new Date(1623250800000L),
                    Arrays.asList("tita@hotmail.fr")
            )
    );

    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    public int idMeeting;

    public String room;

    public String meetingSubject;

    public Date meetingDateDebut;

    public Date meetingDateFin;

    public List<String> meetingGuestListId;

    public Meeting(int idMeeting, String room, String meetingSubject, Date meetingDateDebut, Date meetingDateFin, List<String> meetingGuestListId) {
        this.idMeeting = idMeeting;
        this.room = room;
        this.meetingSubject = meetingSubject;
        this.meetingDateDebut = meetingDateDebut;
        this.meetingDateFin = meetingDateFin;
        this.meetingGuestListId = meetingGuestListId;
    }

    public long getIdMeeting() {
        return idMeeting;
    }

    public String getRoom() {
        return room;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public Date getMeetingDateDebut() {
        return meetingDateDebut;
    }

    public Date getMeetingDateFin() {
        return meetingDateFin;
    }

    public List<String> getMeetingGuestListId() {
        return meetingGuestListId;
    }
}