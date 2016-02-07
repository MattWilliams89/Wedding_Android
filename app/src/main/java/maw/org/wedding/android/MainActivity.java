package maw.org.wedding.android;

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

import org.maw.wedding.navigation.Bus;
import org.maw.wedding.navigation.EventBus;
import org.maw.wedding.navigation.NavigationEvent;
import org.maw.wedding.navigation.NavigationEventController;
import org.maw.wedding.navigation.Navigator;
import org.maw.wedding.navigation.ScreenEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maw.org.wedding.R;
import maw.org.wedding.android.home.HomeScreen;
import maw.org.wedding.android.info_section.InfoScreen;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Navigator {

    private NavigationEventController mNavigationController;
    private Bus mEventBus;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.fab)
    FloatingActionButton mActionButton;

    @OnClick(R.id.fab)
    void fabClicked() {
        Snackbar.make(mActionButton, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

        mEventBus = new EventBus();
        mNavigationController = new NavigationEventController(this, mEventBus);

        List<ScreenEvent> events = new ArrayList<>();
        events.add(new ScreenEvent(new HomeScreen(this), R.id.home_event));
        events.add(new ScreenEvent(new InfoScreen(this), R.id.info_event));
        mNavigationController.registerForScreenEvents(events);

        if (savedInstanceState == null) {
            mNavigationController.onEvent(new NavigationEvent(R.id.home_event));
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
    public void navigate(ScreenEvent screenEvent) {
        mNavigationView.setCheckedItem(screenEvent.getId());
        screenEvent.getScreen().show();
    }
}
