package com.mkenlo.activefit.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mkenlo.activefit.db.model.Workout;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert
    void addWorkout(Workout workout);

    @Query("Select * from workout_activities")
    LiveData<List<Workout>> getAllWorkoutActivities();

}
