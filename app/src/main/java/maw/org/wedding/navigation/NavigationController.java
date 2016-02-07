package maw.org.wedding.navigation;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import maw.org.wedding.R;
import maw.org.wedding.navigation.ScreenEvents.HomeScreenEvent;
import maw.org.wedding.navigation.ScreenEvents.InfoScreenEvent;
import maw.org.wedding.navigation.ScreenEvents.ScreenEvent;

public class NavigationController {

    private AndroidNavigator mAndroidNavigator;
    private Bus mEventBus;
    private Map<Integer, ScreenEvent> mNavigationMap;

    public NavigationController(AndroidNavigator androidNavigator, Bus eventBus) {
        mAndroidNavigator = androidNavigator;
        mEventBus = eventBus;

        mNavigationMap = new HashMap<>();
        mNavigationMap.put(R.id.home, new HomeScreenEvent());
        mNavigationMap.put(R.id.nav_camera, new InfoScreenEvent());

    }

    public void onReady() {
        mEventBus.register(this);
    }

    public void cleanUp() {
        mEventBus.unregister(this);
    }

    public void onScreenEvent(ScreenEvent screenEvent) {
        mAndroidNavigator.navigate(screenEvent.getScreen());
    }

    @Subscribe
    public void onNavigationEvent(NavigationEvent navigationEvent) {
        if (mNavigationMap.containsKey(navigationEvent.getEventId())) {
            mAndroidNavigator.navigate(mNavigationMap.get(navigationEvent.getEventId()).getScreen());
        }
    }
}
