package com.example.categoryapp.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.categoryapp.model.User;

@Database(entities = User.class, version = 1, exportSchema = false)

public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDAO getUserDao();

}