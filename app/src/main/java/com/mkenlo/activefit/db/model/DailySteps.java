package com.mkenlo.activefit.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dailysteps")
public class DailySteps {

    public int count;
    public Date date;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public DailySteps() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
