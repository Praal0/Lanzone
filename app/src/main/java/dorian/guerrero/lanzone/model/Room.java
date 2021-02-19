package dorian.guerrero.lanzone.model;

import androidx.annotation.DrawableRes;

import java.util.Arrays;
import java.util.List;

import dorian.guerrero.lanzone.R;

public class Room {

    private final int mId;
    private final String mRoomName;
    private final int mDrawableRes;

    private static final List<Room> DUMMY_ROOMS = Arrays.asList(
            new Room(1, "Etretat", R.drawable.etretas),
            new Room(2, "Himalaya", R.drawable.himalaya),
            new Room(3,"Antartique",R.drawable.antartique),
            new Room (4,"Artique",R.drawable.artique),
            new Room(5,"Milan",R.drawable.milan),
            new Room(6,"Sydney",R.drawable.sydney),
            new Room(7,"Everest",R.drawable.everest),
            new Room(8,"Oslo",R.drawable.oslo),
            new Room(9,"Paris",R.drawable.paris),
            new Room(10,"Barcelone",R.drawable.barcelone)
    );

    public Room(int id, String roomName, @DrawableRes int drawableRes) {
        mId = id;
        mRoomName = roomName;
        mDrawableRes = drawableRes;
    }

// getters...

    @DrawableRes
    public int getDrawableRes() {
        return mDrawableRes;
    }
}
