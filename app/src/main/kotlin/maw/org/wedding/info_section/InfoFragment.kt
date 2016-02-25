package maw.org.wedding.info_section


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import maw.org.wedding.R

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_fragment_layout, container, false)
    }

    companion object {
        fun newInstance(): Fragment {
            return InfoFragment()
        }
    }
}
