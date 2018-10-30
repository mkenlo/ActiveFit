package com.mkenlo.activefit.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mkenlo.activefit.db.dao.DailyStepsDao;
import com.mkenlo.activefit.db.dao.GoalDao;
import com.mkenlo.activefit.db.dao.UserProfileDao;
import com.mkenlo.activefit.db.dao.WorkoutDao;
import com.mkenlo.activefit.db.model.DailySteps;
import com.mkenlo.activefit.db.model.Goals;
import com.mkenlo.activefit.db.model.UserProfile;
import com.mkenlo.activefit.db.model.Workout;


@Database(entities = {Workout.class, UserProfile.class, Goals.class, DailySteps.class}, version = 1)
public abstract class ActiveFitDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();
    public abstract UserProfileDao userProfileDao();
    public abstract GoalDao goalDao();
    public abstract DailyStepsDao dailyStepsDao();

    private static volatile ActiveFitDatabase INSTANCE;

    static ActiveFitDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActiveFitDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ActiveFitDatabase.class, "activefit_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
