package maw.org.wedding;

import org.junit.Test;
import org.maw.wedding.navigation.Bus;
import org.maw.wedding.navigation.NavigationEvent;
import org.maw.wedding.navigation.NavigationEventController;
import org.maw.wedding.navigation.Navigator;
import org.maw.wedding.navigation.Screen;
import org.maw.wedding.navigation.ScreenEvent;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class NavigationControllerTest {

    @Test
    public void testEventDispatched() {

        Navigator mockNavigator = Mockito.mock(Navigator.class);
        NavigationEventController navigationController = new NavigationEventController(mockNavigator, Mockito.mock(Bus.class));

        ScreenEvent screenEvent = new ScreenEvent(mock(Screen.class), 1);

        List<ScreenEvent> screenEvents = new ArrayList<>();
        screenEvents.add(screenEvent);

        navigationController.registerForScreenEvents(screenEvents);
        navigationController.onReady();

        NavigationEvent navigationEvent = new NavigationEvent(1);

        navigationController.onEvent(navigationEvent);

        Mockito.verify(mockNavigator).navigate(screenEvent);
    }



}
