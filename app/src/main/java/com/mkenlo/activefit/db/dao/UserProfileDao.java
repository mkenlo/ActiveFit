package com.mkenlo.activefit.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mkenlo.activefit.db.model.UserProfile;

@Dao
public interface UserProfileDao {


    @Query("SELECT COUNT(*) FROM userprofile")
    int countUsers();

    @Query("SELECT * FROM userprofile where id = 1")
    UserProfile getUserProfile();

    @Query("SELECT * FROM userprofile where id = 1")
    LiveData<UserProfile> getLiveUserProfile();

    @Insert
    void setUserProfile(UserProfile me);

    @Update
    void updateUserProfile(UserProfile meUpdated);
}
