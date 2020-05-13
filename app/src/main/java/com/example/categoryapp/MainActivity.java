package com.example.categoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.categoryapp.data.UserDAO;
import com.example.categoryapp.data.UserDataBase;
import com.example.categoryapp.model.User;

public class MainActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button buttonLogin;
    TextView textViewRegister;
    UserDAO db;
    UserDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        textViewRegister = findViewById(R.id.textViewRegister);


        dataBase = Room.databaseBuilder(this, UserDataBase.class,"User")
                .allowMainThreadQueries()
                .build();
        db = dataBase.getUserDao();

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                User user = db.getUser(email,password);
                if (user != null) {
                    Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                    i.putExtra("User", user);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Unregister User, or incorrect username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}