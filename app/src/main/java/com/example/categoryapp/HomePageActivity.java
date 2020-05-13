package com.example.categoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;
    private SpinnerViewModel spinnerViewModel;
    private EditText editText1;
    ArrayList<Note> exampleList = new ArrayList<>();
    private NoteAdapter adapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final List<com.example.categoryapp.Spinner> arrayList = new ArrayList<>();
        final ArrayAdapter<com.example.categoryapp.Spinner> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);

        spinnerViewModel = ViewModelProviders.of(this).get(SpinnerViewModel.class);
        spinnerViewModel.getAllSpinner().observe(this, new Observer<List<com.example.categoryapp.Spinner>>() {
            @Override
            public void onChanged(@Nullable List<com.example.categoryapp.Spinner> spinners) {
                Log.d("qweqwe", "onChanged: " + spinners);
                arrayAdapter.clear();
                arrayAdapter.addAll(spinnerViewModel.getAllSpinner().getValue());
                arrayAdapter.setNotifyOnChange(true);
            }
        });

        editText1 = findViewById(R.id.editText1);

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        final Spinner spinner = findViewById(R.id.spinner1);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                    noteViewModel.getNodesByCategory(tutorialsName);
                    noteViewModel.getAllNotes().observe(HomePageActivity.this, new Observer<List<Note>>() {
                        @Override
                        public void onChanged(@Nullable List<Note> notes) {
                            Log.d("qweqwe", "onChanged2: " + notes);
                            adapter.update(notes);
                        }
                    });


//                exampleList.clear();
//                exampleList.addAll(noteViewModel.getAllNotes().getValue());

//                    if (tutorialsName.equals("name")) {
//                        List<Note> newList = new ArrayList<>();
//                        for (Note item : exampleList) {
//                            if (item.getCategoryName().toLowerCase().equals("name")) {
//                                newList.add(item);
//                            }
//                        }
//                        adapter.update(newList);
//                    }
//                 else if (tutorialsName.equals("age")) {
//                    List<Note> newList = new ArrayList<>();
//                    for (Note item : exampleList) {
//                        if (item.getCategoryName().equals("age")) {
//                            newList.add(item);
//                        }
//                    }
//                    adapter.update(newList);
//                } else {
//                    adapter.update(exampleList);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().toString().equals("search")) {
                    spinner.setVisibility(View.INVISIBLE);
                    editText1.setVisibility(View.VISIBLE);
                    button.setText("done");
                } else {
                    spinner.setVisibility(View.VISIBLE);
                    editText1.setVisibility(View.INVISIBLE);
                    button.setText("search");
                }
            }
        });

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        FloatingActionButton buttonAddSpinner = findViewById(R.id.add_spinner);
        buttonAddSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SpinnerActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
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
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(HomePageActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new NoteAdapter.onItemClickListener1() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(HomePageActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_CATEGORYNAME, note.getCategoryName());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    private void filter(String text) {
        ArrayList<Note> filteredList = new ArrayList<>();
        exampleList = new ArrayList<>();
        exampleList.addAll(noteViewModel.getAllNotes().getValue());

        for (Note item : exampleList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String categoryName = data.getStringExtra(AddEditNoteActivity.EXTRA_CATEGORYNAME);
            Note note = new Note(title, categoryName);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String categoryName = data.getStringExtra(AddEditNoteActivity.EXTRA_CATEGORYNAME);
            Note note = new Note(title, categoryName);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
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