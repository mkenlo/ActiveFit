package com.mkenlo.activefit.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.mkenlo.activefit.db.model.Workout;

@Dao
public interface WorkoutDao {

    @Insert
    void addWorkout(Workout workout);


}
