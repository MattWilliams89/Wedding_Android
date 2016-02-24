package maw.org.wedding

import org.junit.Test
import org.maw.wedding.navigation.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

class EventBusTest {

    @Test
    fun testEventBusEventsAreReceived() {
        val eventBus = EventBus()
        val mockNavigator = mock(Navigator::class.java)
        val navigationController = NavigationEventController(mockNavigator, eventBus)

        val screenEvent = ScreenEvent(mock(Screen::class.java), 1)

        val screenEvents = ArrayList<ScreenEvent>()
        screenEvents.add(screenEvent)

        navigationController.registerForScreenEvents(screenEvents)
        navigationController.onReady()

        val navigationEvent = NavigationEvent(1)

        eventBus.post(navigationEvent)

        Mockito.verify(mockNavigator).navigate(screenEvent)
    }

    @Test
    fun testListenerOnlyReceivesEventOfCorrectType() {

        val eventBus = EventBus()
        val mockNavigator = mock(Navigator::class.java)
        val navigationController = NavigationEventController(mockNavigator, eventBus)

        val screenEvent = ScreenEvent(mock(Screen::class.java), 1)

        val screenEvents = ArrayList<ScreenEvent>()
        screenEvents.add(screenEvent)

        navigationController.registerForScreenEvents(screenEvents)
        navigationController.onReady()

        val navigationEvent = object : Event {
            override fun getEventId(): Int {
                return 1
            }

            override fun getEventType(): Bus.EVENT_TYPE {
                return Bus.EVENT_TYPE.OTHER
            }
        }

        eventBus.post(navigationEvent)

        Mockito.verifyZeroInteractions(mockNavigator)
    }
}
