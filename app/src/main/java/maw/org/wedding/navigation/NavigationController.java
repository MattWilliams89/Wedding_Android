package maw.org.wedding.navigation;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maw.org.wedding.navigation.ScreenEvents.ScreenEvent;

public class NavigationController {

    private Navigator mNavigator;
    private Bus mEventBus;
    private Map<Integer, ScreenEvent> mNavigationMap = new HashMap<>();

    public NavigationController(Navigator navigator, Bus eventBus) {
        mNavigator = navigator;
        mEventBus = eventBus;
    }

    public void registerEvents(List<ScreenEvent> eventList) {
        for (ScreenEvent screenEvent : eventList) {
            mNavigationMap.put(screenEvent.getId(), screenEvent);
        }
    }

    public void onReady() {
        mEventBus.register(this);
    }

    public void cleanUp() {
        mEventBus.unregister(this);
    }

    public void onScreenEvent(ScreenEvent screenEvent) {
        navigate(screenEvent);
    }

    @Subscribe
    public void onNavigationEvent(NavigationEvent navigationEvent) {
        if (mNavigationMap.containsKey(navigationEvent.getEventId())) {
            navigate(mNavigationMap.get(navigationEvent.getEventId()));
        }
    }

    private void navigate(ScreenEvent screenEvent) {
        mNavigator.navigate(screenEvent.getId(), screenEvent.getScreen());
    }
}
