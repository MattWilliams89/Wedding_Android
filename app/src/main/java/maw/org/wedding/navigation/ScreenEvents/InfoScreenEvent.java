package maw.org.wedding.navigation.ScreenEvents;


import maw.org.wedding.R;
import maw.org.wedding.android.info_section.InfoScreen;
import maw.org.wedding.android.Screen;

public class InfoScreenEvent implements ScreenEvent {
    @Override
    public Screen getScreen() {
        return new InfoScreen();
    }

    @Override
    public int getId() {
        return R.id.info_event;
    }
}
