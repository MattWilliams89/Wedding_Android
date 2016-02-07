package maw.org.wedding;

import org.junit.Test;
import org.maw.wedding.navigation.Bus;
import org.maw.wedding.navigation.EventBus;
import org.maw.wedding.navigation.NavigationController;
import org.maw.wedding.navigation.NavigationEvent;
import org.maw.wedding.navigation.Navigator;
import org.maw.wedding.navigation.Screen;
import org.maw.wedding.navigation.ScreenEvent;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class EventBusTest {

    @Test
    public void testEventBusEventsAreReceived() {
        Bus eventBus = new EventBus();
        Navigator mockNavigator = Mockito.mock(Navigator.class);
        NavigationController navigationController = new NavigationController(mockNavigator, eventBus);

        ScreenEvent mockEvent = Mockito.mock(ScreenEvent.class);
        Mockito.when(mockEvent.getId()).thenReturn(1);

        Screen mockScreen = Mockito.mock(Screen.class);
        Mockito.when(mockEvent.getScreen()).thenReturn(mockScreen);

        List<ScreenEvent> screenEvents = new ArrayList<>();
        screenEvents.add(mockEvent);

        navigationController.registerEvents(screenEvents);
        navigationController.onReady();

        NavigationEvent navigationEvent = new NavigationEvent(1);

        eventBus.post(navigationEvent);

        Mockito.verify(mockNavigator).navigate(Matchers.eq(1), Matchers.eq(mockScreen));
    }
}
