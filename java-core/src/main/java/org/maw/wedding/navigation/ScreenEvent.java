package org.maw.wedding.navigation;

public class ScreenEvent {

    private final Screen mScreen;
    private final int mId;

    public ScreenEvent(Screen screen, int id) {
        mScreen = screen;
        mId = id;
    }

    public Screen getScreen() {
        return mScreen;
    }

    public int getId() {
        return mId;
    }
}
