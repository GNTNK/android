package com.example.categoryapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.categoryapp.data.NoteDao;
import com.example.categoryapp.data.SpinnerDao;

import java.util.List;

public class SpinnerRepository {
    private SpinnerDao spinnerDao;
    private LiveData<List<Spinner>> allSpinner;

    public SpinnerRepository(Application application) {
        SpinnerDataBase dataBase = SpinnerDataBase.getInstance(application);
        spinnerDao = dataBase.spinnerDao();
        allSpinner = spinnerDao.getAllSpinner();
    }

    public void insert(Spinner spinner) {
        new InsertSpinnerAsyncTask(spinnerDao).execute(spinner);
    }

    public void update(Spinner spinner) {
        new UpdateSpinnerAsyncTask(spinnerDao).execute(spinner);
    }

    public void delete(Spinner spinner) {
        new DeleteSpinnerAsyncTask(spinnerDao).execute(spinner);
    }

    public LiveData<List<Spinner>> getAllSpinner() {
        return allSpinner;
    }

    private static class InsertSpinnerAsyncTask extends AsyncTask<Spinner, Void, Void> {
        private SpinnerDao spinnerDao;

        private InsertSpinnerAsyncTask(SpinnerDao spinnerDao) {
            this.spinnerDao = spinnerDao;
        }

        @Override
        protected Void doInBackground(Spinner... spinners) {
            spinnerDao.insert(spinners[0]);
            return null;
        }
    }
    private static class UpdateSpinnerAsyncTask extends AsyncTask<Spinner, Void, Void> {
        private SpinnerDao spinnerDao;

        private UpdateSpinnerAsyncTask(SpinnerDao spinnerDao) {
            this.spinnerDao = spinnerDao;
        }

        @Override
        protected Void doInBackground(Spinner... spinners) {
            spinnerDao.update(spinners[0]);
            return null;
        }
    }

    private static class DeleteSpinnerAsyncTask extends AsyncTask<Spinner, Void, Void> {
        private SpinnerDao spinnerDao;

        private DeleteSpinnerAsyncTask(SpinnerDao spinnerDao) {
            this.spinnerDao = spinnerDao;
        }

        @Override
        protected Void doInBackground(Spinner... spinners) {
            spinnerDao.delete(spinners[0]);
            return null;
        }
    }
}
