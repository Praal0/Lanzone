package dorian.guerrero.lanzone.service;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;

import static java.util.Arrays.asList;

public abstract class DummyGeneratorMeeting {
    public static List<Meeting> DUMMY_MEETINGS = asList(
            new Meeting(1,2,"Reunion 1",
                    new DateTime(2021, 3, 15, 12, 0, 0, 0),
                    new DateTime(2021, 3, 15, 13, 0, 0, 0),
                    asList("toto@hotmail.fr","tito@gmail.com")),
            new Meeting(2,7,"Reunion 2",
                    new DateTime(2021, 3, 26, 16, 58, 0, 0),
                    new DateTime(2021, 3, 26, 17, 58, 0, 0),
                    asList("toto@hotmail.fr","tito@gmail.com")),
            new Meeting(3,6,"Reunion 3",
                    new DateTime(2021, 3, 21, 18, 30, 0, 0),
                    new DateTime(2021, 3, 21, 20, 58, 0, 0),
                    asList("toto@hotmail.fr","tito@gmail.com"))
    );

    public static List<Meeting> generateMeetings() {
        return new ArrayList<Meeting>(DUMMY_MEETINGS);
    }

    public static List<Meeting> generateMeetingsTest() {
        return new ArrayList<Meeting>(DUMMY_MEETINGS);}


}
