package com.mkenlo.activefit.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.R;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.UserProfile;
import com.mkenlo.activefit.db.viewmodel.ItemViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment {

    public @BindView(R.id.tv_user_age)    TextView mUserAge;
    public @BindView(R.id.tv_user_gender) TextView mUserGender;
    public @BindView(R.id.tv_user_height) TextView mUserHeight;
    public @BindView(R.id.tv_user_weight) TextView mUserWeight;
    public @BindView(R.id.tv_goal_steps)  TextView mGoalStep;
    public @BindView(R.id.tv_goal_weight)  TextView mGoalWeight;
    public @BindView(R.id.tv_username)    TextView mUsername;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        paintUI();
        return rootView;
    }

    public void paintUI(){
        ItemViewModel viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.getProfile().observe(this, new Observer<UserProfile>() {
            @Override
            public void onChanged(@Nullable UserProfile me) {
                mUserAge.setText(String.valueOf(me.getAge()));
                mUserGender.setText(me.getGender());
                mUserHeight.setText(String.valueOf(me.getHeight()));
                mUserWeight.setText(String.valueOf(me.getWeight()));
                mGoalStep.setText(String.valueOf(me.getTargetSteps()+" Steps"));
                mGoalWeight.setText(String.valueOf(me.getTargetWeight()+" Unit"));
            }
        });
    }
}
