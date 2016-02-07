package maw.org.wedding;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maw.org.wedding.navigation.AndroidNavigator;
import maw.org.wedding.navigation.NavigationController;
import maw.org.wedding.navigation.NavigationEvent;
import maw.org.wedding.navigation.ScreenEvents.HomeScreenEvent;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AndroidNavigator {

    private NavigationController mNavigationController;
    private Bus mEventBus;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.fab)
    FloatingActionButton mActionButton;

    @OnClick(R.id.fab)
    void fabClicked() {
        Snackbar.make(mActionButton, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mEventBus = new Bus();
        mNavigationController = new NavigationController(this, mEventBus);

        if (savedInstanceState == null) {
            mNavigationController.onScreenEvent(new HomeScreenEvent());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNavigationController.onReady();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNavigationController.cleanUp();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mEventBus.post(new NavigationEvent(item.getItemId()));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void navigate(int eventId, Fragment fragment) {
        mNavigationView.setCheckedItem(eventId);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
