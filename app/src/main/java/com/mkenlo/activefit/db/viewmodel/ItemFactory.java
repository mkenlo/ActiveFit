package com.mkenlo.activefit.db.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ItemFactory extends ViewModelProvider.NewInstanceFactory  {

    @NonNull
    private final Application mApplication;
    private final int mItemId;

    public ItemFactory(@NonNull Application application, int itemId) {
        mApplication = application;
        mItemId = itemId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ItemViewModel(mApplication);
    }
}
