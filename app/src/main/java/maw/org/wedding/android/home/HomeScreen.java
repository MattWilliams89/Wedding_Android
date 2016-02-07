package maw.org.wedding.android.home;

import android.app.Activity;

import org.maw.wedding.navigation.Screen;

import maw.org.wedding.R;

public class HomeScreen implements Screen {

    private Activity mActivity;

    public HomeScreen(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void show() {
        mActivity.getFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.newInstance()).commit();
    }
}
