package org.maw.wedding.navigation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationEventController implements EventListener {

    private Navigator mNavigator;
    private Bus mEventBus;
    private Map<Integer, ScreenEvent> mNavigationMap = new HashMap<>();

    public NavigationEventController(Navigator navigator, Bus eventBus) {
        mNavigator = navigator;
        mEventBus = eventBus;
    }

    public void registerForScreenEvents(List<ScreenEvent> eventList) {
        for (ScreenEvent screenEvent : eventList) {
            mNavigationMap.put(screenEvent.getId(), screenEvent);
        }
    }

    public void onReady() {
        mEventBus.subscibe(Bus.EVENT_TYPE.NAVIGATION, this);
    }

    public void cleanUp() {
        mEventBus.unSubscribe(Bus.EVENT_TYPE.NAVIGATION, this);
    }

    @Override
    public void onEvent(Event event) {
        if (mNavigationMap.containsKey(event.getEventId())) {
            navigate(mNavigationMap.get(event.getEventId()));
        }
    }

    private void navigate(ScreenEvent screenEvent) {
        mNavigator.navigate(screenEvent);
    }
}
