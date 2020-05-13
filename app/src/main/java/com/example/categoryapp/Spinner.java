package com.example.categoryapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "spinner_table")
public class Spinner {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String spinner;

    public Spinner(String spinner) {
        this.spinner = spinner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSpinner() {
        return spinner;
    }


    @Override
    public String toString() {
        return spinner;
    }
}