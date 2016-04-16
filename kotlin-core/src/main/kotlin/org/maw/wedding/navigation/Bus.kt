package org.maw.wedding.navigation

interface Bus {

    fun subscribe(listener: EventListener)

    fun unSubscribe(listener: EventListener)

    fun post(event: Event)
}
