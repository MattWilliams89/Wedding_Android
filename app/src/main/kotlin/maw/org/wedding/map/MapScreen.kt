package maw.org.wedding.map

import android.app.Activity

import org.maw.wedding.navigation.Screen

import maw.org.wedding.R

class MapScreen(private val mActivity: Activity) : Screen {

    override fun show() {
        mActivity.fragmentManager.beginTransaction().replace(R.id.container, MapLocationFragment.create()).commit()
    }
}
