package com.mkenlo.activefit.db.model;

import java.util.Date;

public class DailySteps {

    public int count;
    public Date date;

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
