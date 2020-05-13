package com.example.categoryapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.categoryapp.Spinner;

import java.util.List;

@Dao
public interface SpinnerDao {

    @Insert
    void insert(Spinner spinner);
    @Update
    void update(Spinner spinner);
    @Delete
    void delete(Spinner spinner);

    @Query("SELECT * FROM spinner_table")
    LiveData<List<Spinner>> getAllSpinner();
}
