package dorian.guerrero.lanzone.service;

import java.util.ArrayList;
import java.util.List;

import dorian.guerrero.lanzone.R;
import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.model.Room;

import static java.util.Arrays.asList;

public abstract class GeneratorRoom {

    private static List<Room> DUMMY_ROOM = asList(
            new Room(1,"Mario", "R.drawable.mario"),
            new Room(2,"Luigi", "R.drawable.luigi"),
            new Room(3,"Ness", "R.drawable.ness"),
            new Room(4,"Fox", "R.drawable.fox"),
            new Room(5,"Kong", "R.drawable.kong"),
            new Room(6,"Pikachu", "R.drawable.pikachu"),
            new Room(7,"Yoshi", "R.drawable.yoshi"),
            new Room(8,"Falcom", "R.drawable.falcom"),
            new Room(9,"Corrin", "R.drawable.corrin"),
            new Room(10,"Kirby", "R.drawable.kirby")
    );

    static List<Room> generateRoom() {
        return new ArrayList<Room>(DUMMY_ROOM);
    }

}
