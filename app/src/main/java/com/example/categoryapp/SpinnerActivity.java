package com.example.categoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity {
    public static final int ADD_SPINNER_REQUEST = 3;
    public static final int EDIT_SPINNER_REQUEST = 4;
    private SpinnerViewModel spinnerViewModel;
    private SpinnerAdapter adapter;
    public ArrayList<Spinner> spinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        setTitle("Spinners");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        FloatingActionButton buttonAddSpinner = findViewById(R.id.button_add_spinner);
        buttonAddSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpinnerActivity.this, AddEditSpinnerActivity.class);
                startActivityForResult(intent, ADD_SPINNER_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_spinner_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SpinnerAdapter adapter = new SpinnerAdapter();
        recyclerView.setAdapter(adapter);

        spinnerViewModel = ViewModelProviders.of(this).get(SpinnerViewModel.class);
        spinnerViewModel.getAllSpinner().observe(this, new Observer<List<Spinner>>() {
            @Override
            public void onChanged(@Nullable List<Spinner> spinners) {
                adapter.setSpinners(spinners);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                spinnerViewModel.delete(adapter.getSpinnerAt(viewHolder.getAdapterPosition()));
                Toast.makeText(SpinnerActivity.this, "Spinner deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new SpinnerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Spinner spinner) {
                Intent intent = new Intent(SpinnerActivity.this, AddEditSpinnerActivity.class);
                intent.putExtra(AddEditSpinnerActivity.EXTRA_ID, spinner.getId());
                intent.putExtra(AddEditSpinnerActivity.EXTRA_SPINNER, spinner.getSpinner());
                startActivityForResult(intent, EDIT_SPINNER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SPINNER_REQUEST && resultCode == RESULT_OK) {
            String categoryName = data.getStringExtra(AddEditSpinnerActivity.EXTRA_SPINNER);

            Spinner spinner = new Spinner(categoryName);
            spinnerViewModel.insert(spinner);

            Toast.makeText(this, "Spinner saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_SPINNER_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditSpinnerActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Spinner can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String categoryName = data.getStringExtra(AddEditSpinnerActivity.EXTRA_SPINNER);

            Spinner spinner = new Spinner(categoryName);
            spinner.setId(id);
            spinnerViewModel.update(spinner);

            Toast.makeText(this, "Spinner updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Spinner not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
