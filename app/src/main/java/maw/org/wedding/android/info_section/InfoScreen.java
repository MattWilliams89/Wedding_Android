package maw.org.wedding.android.info_section;

import android.app.Fragment;

import maw.org.wedding.android.Screen;

public class InfoScreen implements Screen {
    @Override
    public Fragment getFragment() {
        return InfoFragment.newInstance();
    }
}
