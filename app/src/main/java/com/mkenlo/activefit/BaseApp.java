package com.mkenlo.activefit;

import android.app.Application;

import com.mkenlo.activefit.db.ActiveFitDatabase;
import com.mkenlo.activefit.db.ActiveFitRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private ActiveFitDatabase getDatabase() {
        return ActiveFitDatabase.getDatabase(this);
    }

    public ActiveFitRepository getRepository() {
        return ActiveFitRepository.getInstance(getDatabase());
    }
}
