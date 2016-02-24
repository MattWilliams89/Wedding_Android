package org.maw.wedding.navigation

import java.util.*

class EventBus : Bus {

    private val mListenerMap = HashMap<Bus.EVENT_TYPE, MutableList<EventListener>>()

    init {
        mListenerMap.put(Bus.EVENT_TYPE.NAVIGATION, ArrayList<EventListener>())
        mListenerMap.put(Bus.EVENT_TYPE.OTHER, ArrayList<EventListener>())
    }

    override fun subscibe(eventType: Bus.EVENT_TYPE, listener: EventListener) {
        mListenerMap.get(eventType)!!.add(listener)
    }

    override fun unSubscribe(eventType: Bus.EVENT_TYPE, listener: EventListener) {
        mListenerMap.get(eventType)!!.remove(listener)
    }

    override fun post(event: Event) {
        for (listener in mListenerMap[event.getEventType()]!!) {
            listener.onEvent(event)
        }
    }
}
