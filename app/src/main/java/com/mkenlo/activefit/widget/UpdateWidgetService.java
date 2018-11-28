package com.mkenlo.activefit.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.R;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.DailySteps;

public class UpdateWidgetService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(
                this.getApplicationContext());
        final ComponentName theWidget = new ComponentName(this, ActiveFitWidget.class);



        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                RemoteViews view = new RemoteViews(getPackageName(), R.layout.active_fit_widget);
                ActiveFitRepository repo = ((BaseApp) getApplication()).getRepository();
                DailySteps todayActivity = repo.getTodayActivity();
                if(todayActivity != null){
                    view.setTextViewText(R.id.appwidget_num_steps, String.valueOf(todayActivity.getCount()));
                    appWidgetManager.updateAppWidget(theWidget, view);
                }

            }
        });


        return super.onStartCommand(intent, flags, startId);
    }


}
