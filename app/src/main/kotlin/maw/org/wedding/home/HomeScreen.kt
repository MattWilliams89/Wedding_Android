package maw.org.wedding.home

import android.app.Activity

import org.maw.wedding.navigation.Screen

import maw.org.wedding.R

class HomeScreen(private val mActivity: Activity) : Screen {

    override fun show() {
        mActivity.fragmentManager.beginTransaction().replace(R.id.container, HomeFragment.newInstance()).commit()
    }
}
