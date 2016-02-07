package maw.org.wedding.info_section;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import maw.org.wedding.R;

public class InfoFragment extends Fragment {

    public static Fragment newInstance() {
        return new InfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.info_fragment_layout, container, false);
        return rootView;
    }
}
