package org.maw.wedding.navigation

class NavigationEventBus : Bus {

    val mListenerMap: MutableList<EventListener> = arrayListOf()

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
