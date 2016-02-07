package maw.org.wedding.android.home;

import android.app.Fragment;

import maw.org.wedding.android.Screen;

public class HomeScreen implements Screen {
    @Override
    public Fragment getFragment() {
        return HomeFragment.newInstance();
    }
}
