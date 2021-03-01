package dorian.guerrero.lanzone.model;

import androidx.annotation.DrawableRes;

public class Room {
    private final long mId;
    private final String mRoomName;
    private final int mDrawableRes;

    public Room(long id, String roomName, @DrawableRes int drawableRes) {
        mId = id;
        mRoomName = roomName;
        mDrawableRes = drawableRes;
    }

    // getters...

    public long getId(){
        return mId;
    }

    public String getRoomName(){
        return mRoomName;
    }

    @DrawableRes
    public int getDrawableRes() {
        return mDrawableRes;
    }
}
