package org.maw.wedding.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus implements Bus {

    private Map<EVENT_TYPE, List<EventListener>> mListenerMap = new HashMap<>();

    public EventBus() {
        mListenerMap.put(EVENT_TYPE.NAVIGATION, new ArrayList<EventListener>());
        mListenerMap.put(EVENT_TYPE.OTHER, new ArrayList<EventListener>());
    }

    @Override
    public void subscibe(EVENT_TYPE eventType, EventListener listener) {
        mListenerMap.get(eventType).add(listener);
    }

    @Override
    public void unSubscribe(EVENT_TYPE eventType, EventListener listener) {
        mListenerMap.get(eventType).remove(listener);
    }

    @Override
    public void post(Event event) {
        for (EventListener listener : mListenerMap.get(event.getEventType())) {
            listener.onEvent(event);
        }
    }
}
