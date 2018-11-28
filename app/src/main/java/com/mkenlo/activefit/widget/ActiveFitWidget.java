package com.mkenlo.activefit.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.MainActivity;
import com.mkenlo.activefit.R;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.DailySteps;
import com.mkenlo.activefit.db.model.UserProfile;
import com.mkenlo.activefit.db.viewmodel.ItemViewModel;

/**
 * Implementation of App Widget functionality.
 */
public class ActiveFitWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        final CharSequence widgetText = "0";

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(
                context.getPackageName(), R.layout.active_fit_widget);
        views.setTextViewText(R.id.appwidget_num_steps, widgetText);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,0, intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_num_steps, pendingIntent);

        Intent intentService = new Intent(context, UpdateWidgetService.class);
        context.startService(intentService);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_num_steps);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}

