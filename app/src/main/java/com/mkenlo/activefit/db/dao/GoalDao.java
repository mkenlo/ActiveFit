package com.mkenlo.activefit.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.mkenlo.activefit.db.model.Goals;

@Dao
public interface GoalDao {

    @Insert
    void setGoal(Goals goal);

    @Update
    void updateGoal(Goals goal);

    @Query("SELECT * FROM fitness_goals")
    Goals getGoals();
}
