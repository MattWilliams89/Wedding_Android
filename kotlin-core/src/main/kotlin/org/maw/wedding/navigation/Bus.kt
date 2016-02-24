package org.maw.wedding.navigation

interface Bus {
    enum class EVENT_TYPE {
        NAVIGATION,
        OTHER
    }

    fun subscibe(eventType: Bus.EVENT_TYPE, listener: EventListener)

    fun unSubscribe(eventType: Bus.EVENT_TYPE, listener: EventListener)

    fun post(event: Event)
}
