package com.mkenlo.activefit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.mkenlo.activefit.fragment.ProfileSetupOneFragment;
import com.mkenlo.activefit.fragment.ProfileSetupThreeFragment;
import com.mkenlo.activefit.fragment.ProfileSetupTwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileSetupActivity extends FragmentActivity {
    static final int NUM_PAGE_SETUP = 3;
    ProfileSetupAdapter mAdapter;
    public @BindView(R.id.pager) ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        ButterKnife.bind(this);

        mAdapter = new ProfileSetupAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == NUM_PAGE_SETUP-1){
                    Intent intent = new Intent(ProfileSetupActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
