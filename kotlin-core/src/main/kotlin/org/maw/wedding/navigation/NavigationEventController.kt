package org.maw.wedding.navigation

import java.util.*


class NavigationEventController(val mNavigator: Navigator, val mEventBus: Bus) : EventListener {
    private val mNavigationMap = HashMap<Int, ScreenEvent>()

    fun registerForScreenEvents(eventList: List<ScreenEvent>) {
        for (screenEvent in eventList) {
            mNavigationMap.put(screenEvent.id, screenEvent)
        }
    }

    fun onReady() {
        mEventBus.subscribe(this)
    }

    fun cleanUp() {
        mEventBus.unSubscribe(this)
    }

    override fun onEvent(event: Event) {
        if (mNavigationMap.containsKey(event.id)) {
            navigate(mNavigationMap[event.id]!!)
        }
    }

    private fun navigate(screenEvent: ScreenEvent) {
        mNavigator.navigate(screenEvent)
    }
}
