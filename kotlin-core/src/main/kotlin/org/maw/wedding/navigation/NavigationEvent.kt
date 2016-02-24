package org.maw.wedding.navigation

class NavigationEvent(private val mId: Int) : Event {

    override fun getEventId(): Int {
        return mId
    }

    override fun getEventType(): Bus.EVENT_TYPE {
        return Bus.EVENT_TYPE.NAVIGATION
    }

}
