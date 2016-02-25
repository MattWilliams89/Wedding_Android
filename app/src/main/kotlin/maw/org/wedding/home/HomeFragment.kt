package maw.org.wedding.home

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import maw.org.wedding.R

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    companion object {
        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }
}
