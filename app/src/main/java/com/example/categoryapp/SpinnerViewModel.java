package com.example.categoryapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class SpinnerViewModel extends AndroidViewModel {
    private SpinnerRepository repository;
    private LiveData<List<Spinner>> allSpinner;
    public SpinnerViewModel(@NonNull Application application) {
        super(application);

        repository = new SpinnerRepository(application);
        allSpinner = repository.getAllSpinner();
    }

    public void insert(Spinner spinner) {
        repository.insert(spinner);
    }

    public void update(Spinner spinner) {
        repository.update(spinner);
    }

    public void delete(Spinner spinner) {
        repository.delete(spinner);
    }

    public LiveData<List<Spinner>> getAllSpinner() {
        return allSpinner;
    }
}
