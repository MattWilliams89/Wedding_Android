package maw.org.wedding.navigation.ScreenEvents;


import android.app.Fragment;

import maw.org.wedding.info_section.InfoFragment;

public class InfoScreenEvent implements ScreenEvent {
    @Override
    public Fragment getScreen() {
        return InfoFragment.newInstance();
    }
}
