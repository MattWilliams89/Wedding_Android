package maw.org.wedding

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
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

class MainActivity : AppCompatActivity(), Navigator, BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var mNavigationController: NavigationEventController
    lateinit var mEventBus: Bus

    val mNavigationView: BottomNavigationView by bindView(R.id.bottom_navigation)
    val mToolbar: Toolbar by bindView(R.id.toolbar)
    val mDrawerLayout: DrawerLayout by bindView(R.id.drawer_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mToolbar)

        mNavigationView.setOnNavigationItemSelectedListener(this)

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
        mEventBus.post(Event(item.itemId))
        return true
    }

    override fun navigate(screenEvent: ScreenEvent) {
        screenEvent.screen.show()
    }
}
