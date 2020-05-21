package com.example.categoryapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class AddEditNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_ID =
            "com.example.categoryapp.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.categoryapp.EXTRA_TITLE";
    public static final String EXTRA_CATEGORYNAME =
            "com.example.categoryapp.EXTRA_CATEGORYNAME";
    private EditText editTextTitle;
    String tutorialsName;
    private SpinnerViewModel spinnerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        final List<com.example.categoryapp.Spinner> arrayList = new ArrayList<>();
        final ArrayAdapter<com.example.categoryapp.Spinner> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);

        spinnerViewModel = ViewModelProviders.of(this).get(SpinnerViewModel.class);
        spinnerViewModel.getAllSpinner().observe(this, new Observer<List<com.example.categoryapp.Spinner>>() {
            @Override
            public void onChanged(@Nullable List<com.example.categoryapp.Spinner> spinners) {
                Log.d("qweqwe", "onChanged: " + spinners);
                arrayAdapter.addAll(spinnerViewModel.getAllSpinner().getValue());
                arrayAdapter.setNotifyOnChange(true);
            }
        });
        editTextTitle = findViewById(R.id.edit_text_title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        } else {
            setTitle("Add Note");
        }

        final android.widget.Spinner spinner = findViewById(R.id.spinner_categoryName);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String categoryName = tutorialsName;
        if (title.trim().isEmpty() || categoryName.trim().isEmpty()) {
            Toast.makeText(this, "Please insert Title and Category name", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_CATEGORYNAME, categoryName);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}