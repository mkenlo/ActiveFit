package com.mkenlo.activefit.db.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.DailySteps;
import com.mkenlo.activefit.db.model.UserProfile;

public class ItemViewModel extends AndroidViewModel {

    ActiveFitRepository mRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((BaseApp) application).getRepository();
    }

    public LiveData<UserProfile> getProfile(){
        return mRepository.getLiveUserProfile();
    }

    public  LiveData<DailySteps> getTodayActivity(){
        return mRepository.getLiveActivity();
    }
}
