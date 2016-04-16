package org.maw.wedding.navigation

import java.util.*

class NavigationEventBus : Bus {

    private val mListenerMap = ArrayList<EventListener>()


    override fun subscribe(listener: EventListener) {
        mListenerMap.add(listener)
    }

    override fun unSubscribe(listener: EventListener) {
        mListenerMap.remove(listener)
    }

    override fun post(event: Event) {
        for (listener in mListenerMap) {
            listener.onEvent(event)
        }
    }
}
