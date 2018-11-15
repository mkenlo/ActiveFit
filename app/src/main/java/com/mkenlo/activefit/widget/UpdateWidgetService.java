package com.mkenlo.activefit.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.DailySteps;

public class UpdateWidgetService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(
                this.getApplicationContext());
        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for(int widgetId : allWidgetIds){
            AppExecutors.getInstance().diskIO().execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            ActiveFitRepository repo = ((BaseApp) getApplication()).getRepository();
                            DailySteps today_activity = repo.getTodayActivity();

                        }
                    });
        }



        return super.onStartCommand(intent, flags, startId);
    }


}
