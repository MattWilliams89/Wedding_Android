package org.maw.wedding.navigation;

import org.maw.wedding.navigation.Event;
import org.maw.wedding.navigation.EventListener;

public interface Bus {
    enum EVENT_TYPE {
        NAVIGATION,
        OTHER
    }

    void subscibe(EVENT_TYPE eventType, EventListener listener);

    void unSubscribe(EVENT_TYPE eventType, EventListener listener);

    void post(Event event);
}
