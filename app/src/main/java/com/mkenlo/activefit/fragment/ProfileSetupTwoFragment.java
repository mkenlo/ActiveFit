package com.mkenlo.activefit.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.ProfileSetupActivity;
import com.mkenlo.activefit.R;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.UserProfile;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupTwoFragment extends Fragment implements ProfileSetupActivity.OnStepDoneListener {

    public @BindView(R.id.sb_user_height)
    SeekBar mSbUserHeight;

    public @BindView(R.id.sb_user_weight)
    SeekBar mSbUserWeight;

    public double mUserHeight;
    public double mUserWeight;

    ProfileSetupActivity mActivity;
    public ProfileSetupTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (ProfileSetupActivity) getActivity();
        mActivity.setOnStepDoneListener(this);
    }

    public SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_setup_two, container, false);
        ButterKnife.bind(this, root);
        mSbUserHeight.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSbUserWeight.setOnSeekBarChangeListener(mSeekBarChangeListener);

        return root;
    }

    public void  setProfile(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ActiveFitRepository repo = ((BaseApp) getActivity().getApplication()).getRepository();
                UserProfile user = repo.getUserProfile();
                user.setHeight(mSbUserHeight.getProgress());
                user.setWeight(mSbUserWeight.getProgress());
                repo.updateUserProfile(user);
            }
        });
    }

    @Override
    public void onStepDone() {
        setProfile();
    }


}
