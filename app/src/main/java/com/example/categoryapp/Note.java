package com.example.categoryapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    public static final String COL_CATEGORY_NAME = "category_name";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    @ColumnInfo(name = COL_CATEGORY_NAME)
    private String categoryName;

    public Note(String title, String categoryName) {
        this.title = title;
        this.categoryName = categoryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return title;
    }
}