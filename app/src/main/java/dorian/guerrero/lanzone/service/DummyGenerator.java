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

public abstract class DummyGenerator {
    private static List<Meeting> DUMMY_MEETINGS = asList(
            new Meeting(1,2,"Reunion 1",
                    new Date(),
                    new DateTime(2005, 3, 26, 12, 0, 0, 0),
                    new DateTime(DateTime.now()),
                    asList("toto@hotmail.fr","tito@gmail.com")),
            new Meeting(2,7,"Reunion 2",
                    new Date(),
                    new DateTime(2005, 3, 26, 16, 58, 0, 0),
                    new DateTime(DateTime.now()),
                    asList("toto@hotmail.fr","tito@gmail.com")),
            new Meeting(3,6,"Reunion 3",
                    new Date(),
                    new DateTime(2005, 3, 26, 18, 30, 0, 0),
                    new DateTime(DateTime.now()),
                    asList("toto@hotmail.fr","tito@gmail.com"))
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<Meeting>(DUMMY_MEETINGS);
    }


}
