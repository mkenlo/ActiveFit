package com.mkenlo.activefit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mkenlo.activefit.db.ActiveFitDatabase;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.UserProfile;
import com.mkenlo.activefit.fragment.ProfileSetupOneFragment;
import com.mkenlo.activefit.fragment.ProfileSetupThreeFragment;
import com.mkenlo.activefit.fragment.ProfileSetupTwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileSetupActivity extends FragmentActivity {
    static final int NUM_PAGE_SETUP = 3;
    ProfileSetupAdapter mAdapter;
    public @BindView(R.id.pager) ViewPager mPager;
    OnStepDoneListener mStepDoneListener;
    public OnStepDoneListener[] mStepFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        ButterKnife.bind(this);

        mStepFragments = new OnStepDoneListener[]{ new ProfileSetupOneFragment(), new ProfileSetupTwoFragment(),
                new ProfileSetupThreeFragment()};
        mAdapter = new ProfileSetupAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

               if(position <= mStepFragments.length && position >0){
                    mStepFragments[position-1].onStepDone();
               }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        initUser();

    }

    public class ProfileSetupAdapter extends FragmentPagerAdapter {

        public ProfileSetupAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return (Fragment) mStepFragments[0];
                case 1:
                    return (Fragment) mStepFragments[1];
                case 2:
                    return (Fragment) mStepFragments[2];
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGE_SETUP;
        }
    }

    public void  initUser(){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ActiveFitRepository repo = ((BaseApp)getApplication()).getRepository();
                repo.initProfile();
            }
        });
    }

    public void setOnStepDoneListener(OnStepDoneListener listener) {
        this.mStepDoneListener = listener;
    }

    public interface OnStepDoneListener{
        void onStepDone();
    }


}
