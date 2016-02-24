package org.maw.wedding.navigation

import java.util.*


class NavigationEventController(private val mNavigator: Navigator, private val mEventBus: Bus) : EventListener {
    private val mNavigationMap = HashMap<Int, ScreenEvent>()

    fun registerForScreenEvents(eventList: List<ScreenEvent>) {
        for (screenEvent in eventList) {
            mNavigationMap.put(screenEvent.id, screenEvent)
        }
    }

    fun onReady() {
        mEventBus.subscibe(Bus.EVENT_TYPE.NAVIGATION, this)
    }

    fun cleanUp() {
        mEventBus.unSubscribe(Bus.EVENT_TYPE.NAVIGATION, this)
    }

    override fun onEvent(event: Event) {
        if (mNavigationMap.containsKey(event.getEventId())) {

            navigate(mNavigationMap[event.getEventId()]!!)
        }
    }

    private fun navigate(screenEvent: ScreenEvent) {
        mNavigator.navigate(screenEvent)
    }
}
