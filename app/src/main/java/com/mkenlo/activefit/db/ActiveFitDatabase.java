package com.mkenlo.activefit.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mkenlo.activefit.db.dao.DailyStepsDao;
import com.mkenlo.activefit.db.dao.UserProfileDao;
import com.mkenlo.activefit.db.dao.WorkoutDao;
import com.mkenlo.activefit.db.model.DailySteps;
import com.mkenlo.activefit.db.model.UserProfile;
import com.mkenlo.activefit.db.model.Workout;


@Database(entities = {Workout.class, UserProfile.class, DailySteps.class}, version = 1, exportSchema = false)
public abstract class ActiveFitDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();
    public abstract UserProfileDao userProfileDao();
    public abstract DailyStepsDao dailyStepsDao();
    public static String DATABASE_NAME = "activefit_database";

    private static ActiveFitDatabase INSTANCE;
    private static final Object LOCK = new Object();

    public static ActiveFitDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ActiveFitDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return INSTANCE;
    }
}
