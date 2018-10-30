package com.mkenlo.activefit.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.mkenlo.activefit.db.dao.DailyStepsDao;
import com.mkenlo.activefit.db.dao.GoalDao;
import com.mkenlo.activefit.db.dao.UserProfileDao;
import com.mkenlo.activefit.db.dao.WorkoutDao;
import com.mkenlo.activefit.db.model.Workout;

import java.util.List;

public class ActiveFitRepository {
    private WorkoutDao mWorkoutDao;
    private LiveData<List<Workout>> mAllWorkout;

    private UserProfileDao mUserProfileDao;
    private DailyStepsDao mDailyStepDao;
    private GoalDao mGoalDao;

    ActiveFitRepository(Application application) {
        ActiveFitDatabase db = ActiveFitDatabase.getDatabase(application);
        mWorkoutDao = db.workoutDao();
        mAllWorkout = mWorkoutDao.getAllWorkoutActivities();

        mUserProfileDao = db.userProfileDao();
        mDailyStepDao = db.dailyStepsDao();
        mGoalDao = db.goalDao();
    }

    LiveData<List<Workout>> getAllWorkoutActivities() {
        return mAllWorkout;
    }


    public void addWorkout(Workout workout) {
        new insertAsyncTask(mWorkoutDao).execute(workout);
    }

    private static class insertAsyncTask extends AsyncTask<Workout, Void, Void> {

        private WorkoutDao mAsyncTaskDao;

        insertAsyncTask(WorkoutDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Workout... params) {
            mAsyncTaskDao.addWorkout(params[0]);
            return null;
        }
    }
}
