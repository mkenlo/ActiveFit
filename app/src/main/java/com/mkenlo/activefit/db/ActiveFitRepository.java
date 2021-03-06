package com.mkenlo.activefit.db;

import android.arch.lifecycle.LiveData;
import com.mkenlo.activefit.db.dao.DailyStepsDao;
import com.mkenlo.activefit.db.dao.UserProfileDao;
import com.mkenlo.activefit.db.dao.WorkoutDao;
import com.mkenlo.activefit.db.model.DailySteps;
import com.mkenlo.activefit.db.model.UserProfile;
import com.mkenlo.activefit.db.model.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ActiveFitRepository {
    private WorkoutDao mWorkoutDao;
    private UserProfileDao mUserProfileDao;
    private DailyStepsDao mDailyStepDao;
    private static ActiveFitRepository sInstance;
    private ActiveFitDatabase mDatabase;

    public ActiveFitRepository(final ActiveFitDatabase database) {
        mDatabase = database;
        mWorkoutDao = mDatabase.workoutDao();
        mUserProfileDao = mDatabase.userProfileDao();
        mDailyStepDao = mDatabase.dailyStepsDao();
    }

    public static ActiveFitRepository getInstance(final ActiveFitDatabase database) {
        if (sInstance == null) {
            synchronized (ActiveFitRepository.class) {
                if (sInstance == null) {
                    sInstance = new ActiveFitRepository(database);
                }
            }
        }
        return sInstance;
    }

    LiveData<List<Workout>> getAllWorkoutActivities() {
        return mWorkoutDao.getAllWorkoutActivities();
    }

    public void addWorkout(Workout workout) {
        mWorkoutDao.addWorkout(workout);
    }

    public boolean isProfileSetup(){
        return (mUserProfileDao.countUsers()>0)?true:false;
    }

    public void initProfile(){
        mUserProfileDao.setUserProfile(new UserProfile());
    }

    public LiveData<UserProfile> getLiveUserProfile(){
        return mUserProfileDao.getLiveUserProfile();
    }

    public UserProfile getUserProfile(){
        return mUserProfileDao.getUserProfile();
    }
    public void updateUserProfile(UserProfile updateProfile){
        mDatabase.userProfileDao().updateUserProfile(updateProfile);
    }

    public DailySteps getTodayActivity(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String today = sdf.format(new Date());
        DailySteps todayStep = mDailyStepDao.getTodaySteps(today);
        return todayStep;
    }

    public LiveData<DailySteps> getLiveActivity(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String today = sdf.format(new Date());
        return mDailyStepDao.getLiveTodaySteps(today);
    }

    public void updateTodayActivity(DailySteps activity){
        mDailyStepDao.updateSteps(activity);
    }

    public void insertTodayActivity(DailySteps activity){
        mDailyStepDao.insertSteps(activity);
    }
}
