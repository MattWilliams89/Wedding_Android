package maw.org.wedding.info_section

import android.app.Activity

import org.maw.wedding.navigation.Screen

import maw.org.wedding.R

class InfoScreen(private val mActivity: Activity) : Screen {

    override fun show() {
        mActivity.fragmentManager.beginTransaction().replace(R.id.container, InfoFragment.newInstance()).commit()
    }
}
