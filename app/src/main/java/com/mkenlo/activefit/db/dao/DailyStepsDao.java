package com.mkenlo.activefit.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mkenlo.activefit.db.model.DailySteps;

@Dao
public interface DailyStepsDao {

    @Insert
    void insertSteps(DailySteps steps);

    @Update
    void updateSteps(DailySteps steps);


    @Query("SELECT * FROM DailySteps")
    DailySteps getDailySteps();
}
