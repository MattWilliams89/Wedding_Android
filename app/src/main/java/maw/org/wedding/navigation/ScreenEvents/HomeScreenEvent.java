package maw.org.wedding.navigation.ScreenEvents;

import android.app.Fragment;

import maw.org.wedding.home.HomeFragment;

public class HomeScreenEvent implements ScreenEvent {
    @Override
    public Fragment getScreen() {
        return HomeFragment.newInstance();
    }
}
