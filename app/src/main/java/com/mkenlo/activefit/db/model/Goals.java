package com.mkenlo.activefit.db.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "fitness_goals")
public class Goals {

    double weight;
    int steps;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Goals() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
