package org.maw.wedding.navigation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationController implements NavigationListener{

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
        mEventBus.subscibe(this);
    }

    public void cleanUp() {
        mEventBus.unSubscribe(this);
    }

    @Override
    public void onNavigationEvent(NavigationEvent navigationEvent) {
        if (mNavigationMap.containsKey(navigationEvent.getEventId())) {
            navigate(mNavigationMap.get(navigationEvent.getEventId()));
        }
    }

    private void navigate(ScreenEvent screenEvent) {
        mNavigator.navigate(screenEvent.getId(), screenEvent.getScreen());
    }
}
