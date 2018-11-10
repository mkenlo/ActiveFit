package com.mkenlo.activefit.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.MainActivity;
import com.mkenlo.activefit.ProfileSetupActivity;
import com.mkenlo.activefit.R;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.UserProfile;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupThreeFragment extends Fragment implements ProfileSetupActivity.OnStepDoneListener {

    public @BindView(R.id.sp_user_target_steps) Spinner mListTargetSteps;
    public @BindView(R.id.sb_user_target_weight) SeekBar mUserWeightTarget;
    public @BindView(R.id.setup_done) Button mSetupDone;

    String mSelectedStepTarget;
    ProfileSetupActivity mActivity;

    public ProfileSetupThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (ProfileSetupActivity) getActivity();
        mActivity.setOnStepDoneListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_setup_three, container, false);
        ButterKnife.bind(this, rootView);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.daily_goals_list)
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mListTargetSteps.setAdapter(dataAdapter);

        mListTargetSteps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedStepTarget = parent.getSelectedItem().toString();
                mSelectedStepTarget = mSelectedStepTarget.split(" ")[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSetupDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStepDone();
                Intent intent = new Intent(getContext(),  MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void  setTargetGoals(final String steps, final double weight){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ActiveFitRepository repo = ((BaseApp)getActivity().getApplication()).getRepository();
                UserProfile user = repo.getUserProfile();
                user.setTargetSteps(Integer.valueOf(steps));
                user.setTargetWeight(weight);
                repo.updateUserProfile(user);
            }
        });
    }

    public void validateInput(){
        if (mSelectedStepTarget == null || mSelectedStepTarget ==""){
            String easy_goal = getResources().getResourceEntryName(R.string.daily_goals_easy);
            mSelectedStepTarget = easy_goal.split(" ")[0];
        }
    }

    @Override
    public void onStepDone() {
        validateInput();
        setTargetGoals(mSelectedStepTarget, mUserWeightTarget.getProgress());
    }


}
