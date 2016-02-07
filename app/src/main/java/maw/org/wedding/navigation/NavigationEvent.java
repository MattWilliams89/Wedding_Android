package maw.org.wedding.navigation;

public class NavigationEvent {
    private final int mId;

    public NavigationEvent(int id) {
        mId = id;
    }

    public int getEventId() {
        return mId;
    }
}
