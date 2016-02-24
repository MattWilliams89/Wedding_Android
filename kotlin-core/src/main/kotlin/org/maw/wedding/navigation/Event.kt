package org.maw.wedding.navigation

interface Event {
    open fun getEventId(): Int
    open fun getEventType(): Bus.EVENT_TYPE
}
