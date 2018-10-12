package com.mkenlo.activefit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mkenlo.activefit.fragment.ProfileSetupOneFragment;
import com.mkenlo.activefit.fragment.ProfileSetupThreeFragment;
import com.mkenlo.activefit.fragment.ProfileSetupTwoFragment;

public class ProfileSetupActivity extends FragmentActivity {
    static final int NUM_PAGE_SETUP = 3;
    ProfileSetupAdapter mAdapter;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        mAdapter = new ProfileSetupAdapter(getSupportFragmentManager());

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Watch for button clicks.
        Button button = findViewById(R.id.goto_first);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = findViewById(R.id.goto_last);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(NUM_PAGE_SETUP-1);
            }
        });
    }



    public class ProfileSetupAdapter extends FragmentPagerAdapter {

        public ProfileSetupAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ProfileSetupOneFragment();
                case 1:
                    return new ProfileSetupTwoFragment();
                case 2:
                    return new ProfileSetupThreeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGE_SETUP;
        }
    }
}
