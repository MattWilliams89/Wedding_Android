package maw.org.wedding.util

import org.maw.wedding.navigation.Screen

class TestableScreen: Screen {

    var showCalled: Boolean = false

    override fun show() {
        showCalled = true
    }
}