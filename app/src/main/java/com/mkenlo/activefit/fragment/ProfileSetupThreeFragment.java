package com.mkenlo.activefit.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mkenlo.activefit.HomeActivity;
import com.mkenlo.activefit.ProfileSetupActivity;
import com.mkenlo.activefit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupThreeFragment extends Fragment {

    @BindView(R.id.ib_done) ImageButton mIbDone;
    public ProfileSetupThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_setup_three, container, false);
        ButterKnife.bind(this, rootView);
        mIbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
