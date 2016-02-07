package maw.org.wedding.navigation.ScreenEvents;

import maw.org.wedding.R;
import maw.org.wedding.android.home.HomeScreen;
import maw.org.wedding.android.Screen;

public class HomeScreenEvent implements ScreenEvent {
    @Override
    public Screen getScreen() {
        return new HomeScreen();
    }

    @Override
    public int getId() {
        return R.id.home_event;
    }
}
