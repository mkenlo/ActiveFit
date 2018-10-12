package com.mkenlo.activefit.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkenlo.activefit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupTwoFragment extends Fragment {


    public ProfileSetupTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_setup_two, container, false);
    }

}
