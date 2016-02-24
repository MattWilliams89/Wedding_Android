package maw.org.wedding

import org.junit.Test
import org.maw.wedding.navigation.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

class NavigationControllerTest {

    @Test
    fun testEventDispatched() {

        val mockNavigator = Mockito.mock(Navigator::class.java)
        val navigationController = NavigationEventController(mockNavigator, Mockito.mock(Bus::class.java))

        val screenEvent = ScreenEvent(mock(Screen::class.java), 1)

        val screenEvents = ArrayList<ScreenEvent>()
        screenEvents.add(screenEvent)

        navigationController.registerForScreenEvents(screenEvents)
        navigationController.onReady()

        val navigationEvent = NavigationEvent(1)

        navigationController.onEvent(navigationEvent)

        Mockito.verify(mockNavigator).navigate(screenEvent)
    }


}
