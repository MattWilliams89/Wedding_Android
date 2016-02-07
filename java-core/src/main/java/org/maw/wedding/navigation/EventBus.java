package org.maw.wedding.navigation;

import java.util.ArrayList;
import java.util.List;

public class EventBus implements Bus {

    private List<NavigationListener> mListenerList = new ArrayList<>();

    @Override
    public void subscibe(NavigationListener listener) {
        mListenerList.add(listener);
    }

    @Override
    public void unSubscribe(NavigationListener listener) {
        mListenerList.remove(listener);
    }

    @Override
    public void post(NavigationEvent navigationEvent) {
        for (NavigationListener listener : mListenerList) {
            listener.onNavigationEvent(navigationEvent);
        }
    }
}
