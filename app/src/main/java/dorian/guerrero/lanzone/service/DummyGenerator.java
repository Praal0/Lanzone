package dorian.guerrero.lanzone.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;

import static java.util.Arrays.asList;

public abstract class DummyGenerator {

    private static ArrayList<String> participant = (ArrayList<String>) asList("praal@hotmail.fr","toto@hotmail.fr");


    private static List<Meeting> DUMMY_MEETINGS = asList(
            new Meeting(1,2,"Reunion 1","",
                    "",  participant),
            new Meeting(2,7,"Reunion 2","",
                                "",  participant),
            new Meeting(3,6,"Reunion 3","",
                    "",  participant)
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<Meeting>(DUMMY_MEETINGS);
    }


}
