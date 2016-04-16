package maw.org.wedding.util

import org.maw.wedding.navigation.Navigator
import org.maw.wedding.navigation.ScreenEvent


class TestableNavigator: Navigator {

    var screenEvent: ScreenEvent? = null

    override fun navigate(screenEvent: ScreenEvent) {
        this.screenEvent = screenEvent
    }
}