package maw.org.wedding.navigation.ScreenEvents;

import android.app.Fragment;

import maw.org.wedding.R;
import maw.org.wedding.home.HomeFragment;

public class HomeScreenEvent implements ScreenEvent {
    @Override
    public Fragment getScreen() {
        return HomeFragment.newInstance();
    }

    @Override
    public int getId() {
        return R.id.home_event;
    }
}
