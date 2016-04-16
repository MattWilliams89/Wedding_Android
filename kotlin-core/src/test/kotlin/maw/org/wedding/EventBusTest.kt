package maw.org.wedding

import maw.org.wedding.util.TestableNavigator
import maw.org.wedding.util.TestableScreen
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.maw.wedding.navigation.Event
import org.maw.wedding.navigation.NavigationEventBus
import org.maw.wedding.navigation.NavigationEventController
import org.maw.wedding.navigation.ScreenEvent
import java.util.*

class EventBusTest {

    lateinit var navigator: TestableNavigator

    @Before
    fun setup() {
        navigator = TestableNavigator()
    }

    @Test
    fun testEventBusEventsAreReceived() {
        val eventBus = NavigationEventBus()
        val navigationController = NavigationEventController(navigator, eventBus)

        val screenEvent = ScreenEvent(TestableScreen(), 1)

        val screenEvents = ArrayList<ScreenEvent>()
        screenEvents.add(screenEvent)

        navigationController.registerForScreenEvents(screenEvents)
        navigationController.onReady()

        val navigationEvent = Event(1)

        eventBus.post(navigationEvent)

        Assert.assertEquals(navigator.screenEvent, screenEvent)
    }

}
