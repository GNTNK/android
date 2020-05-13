package com.example.categoryapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.categoryapp.data.SpinnerDao;

@Database(entities = {Spinner.class}, version = 1)
public abstract class SpinnerDataBase extends RoomDatabase {
    private static SpinnerDataBase instance;

    public abstract SpinnerDao spinnerDao();

    public static synchronized SpinnerDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SpinnerDataBase.class, "spinner_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SpinnerDao spinnerDao;

        private PopulateDbAsyncTask(SpinnerDataBase db) {
            spinnerDao = db.spinnerDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            spinnerDao.insert(new Spinner("Spinner1"));
            spinnerDao.insert(new Spinner("Spinner2"));
            spinnerDao.insert(new Spinner("Spinner3"));
            return null;
        }
    }
}
