package maw.org.wedding;

import org.junit.Test;
import org.maw.wedding.navigation.Bus;
import org.maw.wedding.navigation.Event;
import org.maw.wedding.navigation.EventBus;
import org.maw.wedding.navigation.NavigationEvent;
import org.maw.wedding.navigation.NavigationEventController;
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
        NavigationEventController navigationController = new NavigationEventController(mockNavigator, eventBus);

        ScreenEvent mockEvent = Mockito.mock(ScreenEvent.class);
        Mockito.when(mockEvent.getId()).thenReturn(1);

        Screen mockScreen = Mockito.mock(Screen.class);
        Mockito.when(mockEvent.getScreen()).thenReturn(mockScreen);

        List<ScreenEvent> screenEvents = new ArrayList<>();
        screenEvents.add(mockEvent);

        navigationController.registerForScreenEvents(screenEvents);
        navigationController.onReady();

        NavigationEvent navigationEvent = new NavigationEvent(1);

        eventBus.post(navigationEvent);

        Mockito.verify(mockNavigator).navigate(Matchers.eq(1), Matchers.eq(mockScreen));
    }

    @Test
    public void testListenerOnlyReceivesEventOfCorrectType() {

        Bus eventBus = new EventBus();
        Navigator mockNavigator = Mockito.mock(Navigator.class);
        NavigationEventController navigationController = new NavigationEventController(mockNavigator, eventBus);

        ScreenEvent mockEvent = Mockito.mock(ScreenEvent.class);
        Mockito.when(mockEvent.getId()).thenReturn(1);

        Screen mockScreen = Mockito.mock(Screen.class);
        Mockito.when(mockEvent.getScreen()).thenReturn(mockScreen);

        List<ScreenEvent> screenEvents = new ArrayList<>();
        screenEvents.add(mockEvent);

        navigationController.registerForScreenEvents(screenEvents);
        navigationController.onReady();

        Event navigationEvent = new Event() {
            @Override
            public int getEventId() {
                return 1;
            }

            @Override
            public Bus.EVENT_TYPE getEventType() {
                return Bus.EVENT_TYPE.OTHER;
            }
        };

        eventBus.post(navigationEvent);

        Mockito.verifyZeroInteractions(mockNavigator);
    }
}
