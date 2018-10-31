package com.mkenlo.activefit.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mkenlo.activefit.R;


import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupThreeFragment extends Fragment {


    public ProfileSetupThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_setup_three, container, false);
        ButterKnife.bind(this, rootView);
        /*mIbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });*/
        return rootView;
    }

}
