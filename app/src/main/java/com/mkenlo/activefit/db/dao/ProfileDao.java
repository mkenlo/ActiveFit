package com.mkenlo.activefit.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mkenlo.activefit.db.model.Profile;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM Profile")
    Profile getProfile();

    @Insert
    void setProfile(Profile me);

    @Update
    void updateProfile(Profile meUpdated);
}
