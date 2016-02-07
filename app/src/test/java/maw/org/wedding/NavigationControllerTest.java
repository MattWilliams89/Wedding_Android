package maw.org.wedding;

import com.squareup.otto.Bus;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import maw.org.wedding.navigation.Navigator;
import maw.org.wedding.navigation.NavigationController;
import maw.org.wedding.navigation.NavigationEvent;
import maw.org.wedding.android.Screen;
import maw.org.wedding.navigation.ScreenEvents.ScreenEvent;

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
