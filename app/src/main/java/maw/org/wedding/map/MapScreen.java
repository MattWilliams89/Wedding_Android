package maw.org.wedding.map;

import android.app.Activity;

import org.maw.wedding.navigation.Screen;

import maw.org.wedding.R;

public class MapScreen implements Screen {

    private Activity mActivity;

    public MapScreen(Activity activity) {

        mActivity = activity;
    }

    @Override
    public void show() {
        mActivity.getFragmentManager().beginTransaction().replace(R.id.container, MapLocationFragment.create()).commit();
    }
}
