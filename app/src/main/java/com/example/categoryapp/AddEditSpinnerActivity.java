package com.example.categoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.categoryapp.R;

public class AddEditSpinnerActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.categoryapp.EXTRA_ID";
    public static final String EXTRA_SPINNER =
            "com.example.categoryapp.EXTRA_SPINNER";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_spinner);
        editText = findViewById(R.id.edit_text_spinner);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Spinner");
            editText.setText(intent.getStringExtra(EXTRA_SPINNER));
        } else {
            setTitle("Add Spinner");
        }
    }

    private void saveNote() {
        String spinner = editText.getText().toString();
        if (spinner.trim().isEmpty()) {
            Toast.makeText(this, "Please insert Title and Category name", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_SPINNER, spinner);

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
        menuInflater.inflate(R.menu.add_spinner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_spinner:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
