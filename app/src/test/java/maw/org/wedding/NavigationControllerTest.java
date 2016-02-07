package maw.org.wedding;

import org.junit.Test;
import org.maw.wedding.navigation.Bus;
import org.maw.wedding.navigation.NavigationController;
import org.maw.wedding.navigation.NavigationEvent;
import org.maw.wedding.navigation.Navigator;
import org.maw.wedding.navigation.Screen;
import org.maw.wedding.navigation.ScreenEvent;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NavigationControllerTest {

    @Test
    public void testEventDispatched() {

        Navigator mockNavigator = mock(Navigator.class);
        NavigationController navigationController = new NavigationController(mockNavigator, mock(Bus.class));

        ScreenEvent mockEvent = mock(ScreenEvent.class);
        when(mockEvent.getId()).thenReturn(1);

        Screen mockScreen = mock(Screen.class);
        when(mockEvent.getScreen()).thenReturn(mockScreen);

        List<ScreenEvent> screenEvents = new ArrayList<>();
        screenEvents.add(mockEvent);

        navigationController.registerEvents(screenEvents);

        NavigationEvent navEvent = new NavigationEvent(1);

        navigationController.onNavigationEvent(navEvent);

        verify(mockNavigator).navigate(eq(1), eq(mockScreen));
    }



}
