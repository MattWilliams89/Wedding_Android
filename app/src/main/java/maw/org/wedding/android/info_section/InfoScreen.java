package maw.org.wedding.android.info_section;

import android.app.Activity;

import org.maw.wedding.navigation.Screen;

import maw.org.wedding.R;

public class InfoScreen implements Screen {

    private Activity mActivity;

    public InfoScreen(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void show() {
        mActivity.getFragmentManager().beginTransaction().replace(R.id.container, InfoFragment.newInstance()).commit();
    }
}
