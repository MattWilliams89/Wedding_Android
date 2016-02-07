package org.maw.wedding.navigation;

public interface Bus {
    void subscibe(NavigationListener listener);

    void unSubscribe(NavigationListener listener);

    void post(NavigationEvent navigationEvent);
}
