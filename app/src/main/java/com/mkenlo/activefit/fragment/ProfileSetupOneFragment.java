package com.mkenlo.activefit.fragment;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.ProfileSetupActivity;
import com.mkenlo.activefit.R;
import com.mkenlo.activefit.db.ActiveFitDatabase;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.UserProfile;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupOneFragment extends Fragment implements ProfileSetupActivity.OnStepDoneListener {

    public @BindView(R.id.tv_user_age)
    EditText mEditUserAge;
    public @BindView(R.id.rg_user_gender)
    RadioGroup mOptionGender;
    String mUserGender;
    int mUserAge;
    ProfileSetupActivity mActivity;


    public ProfileSetupOneFragment() {
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

        View root = inflater.inflate(R.layout.fragment_profile_setup_one, container, false);
        ButterKnife.bind(this, root);


        mEditUserAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 ){
                    mUserAge = Integer.valueOf(s.toString());
                }
            }
        });
        mOptionGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mUserGender = selectGender(checkedId);
            }
        });

        return root;
    }

    public void  setProfile(){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ActiveFitRepository repo = ((BaseApp) getActivity().getApplication()).getRepository();
                UserProfile user = repo.getUserProfile();
                mUserAge = Integer.valueOf(mEditUserAge.getText().toString());
                user.setAge(mUserAge);
                mUserGender = selectGender(mOptionGender.getCheckedRadioButtonId());
                user.setGender(mUserGender);
                repo.updateUserProfile(user);
            }
        });

    }


    public String selectGender(int id){
        switch(id){
            case R.id.rb_gender_male:
                return "male";
            case R.id.rb_gender_female:
                return "female";
            case R.id.rb_no_gender:
                return "no-gender";
        }
        return "no-gender";
    }

    @Override
    public void onStepDone() {
        setProfile();
    }

}
