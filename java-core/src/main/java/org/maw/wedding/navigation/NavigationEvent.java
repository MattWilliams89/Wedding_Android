package org.maw.wedding.navigation;

public class NavigationEvent implements Event {
    private final int mId;

    public NavigationEvent(int id) {
        mId = id;
    }

    @Override
    public int getEventId() {
        return mId;
    }

    @Override
    public Bus.EVENT_TYPE getEventType() {
        return Bus.EVENT_TYPE.NAVIGATION;
    }

}
