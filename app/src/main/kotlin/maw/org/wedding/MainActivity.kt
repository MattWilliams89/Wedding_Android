package maw.org.wedding

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import kotterknife.bindView
import maw.org.wedding.home.HomeScreen
import maw.org.wedding.info_section.InfoScreen
import maw.org.wedding.map.MapScreen
import org.maw.wedding.navigation.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, Navigator {

    var mPendingNavEvent: Event? = null
    lateinit var mNavigationController: NavigationEventController
    lateinit var mEventBus: Bus

    val mNavigationView: NavigationView by bindView(R.id.nav_view)
    val mToolbar: Toolbar by bindView(R.id.toolbar)
    val mDrawerLayout: DrawerLayout by bindView(R.id.drawer_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mToolbar)

        val toggle = object : ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                if (mPendingNavEvent != null) {
                    mEventBus.post(mPendingNavEvent as Event)
                    mPendingNavEvent = null
                }
            }
        }
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mNavigationView.setNavigationItemSelectedListener(this)

        mEventBus = NavigationEventBus()
        mNavigationController = NavigationEventController(this, mEventBus)

        val events = ArrayList<ScreenEvent>()
        events.add(ScreenEvent(HomeScreen(this), R.id.home_event))
        events.add(ScreenEvent(InfoScreen(this), R.id.info_event))
        events.add(ScreenEvent(MapScreen(this), R.id.map_event))

        mNavigationController.registerForScreenEvents(events)

        if (savedInstanceState == null) {
            mNavigationController.onEvent(Event(R.id.home_event))
        }
    }

    override fun onStart() {
        super.onStart()
        mNavigationController.onReady()
    }

    override fun onStop() {
        super.onStop()
        mNavigationController.cleanUp()
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mPendingNavEvent = Event(item.itemId)
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun navigate(screenEvent: ScreenEvent) {
        mNavigationView.setCheckedItem(screenEvent.id)
        screenEvent.screen.show()
    }
}
