package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;

public class LoginActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signUp, signIn;

    DBHelper DB;
    DBCHelper DBC;
    DBTHelper DBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initWidgets();
        signUpClickListen();
        signInClickListen();
    }

    private void signInClickListen() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginDBActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signUpClickListen() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                //Make sure all fields are entered
                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else{
                    //Make sure password and repassword are equal
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        if (!checkuser) {
                            //Check if username is already in DB
                            Boolean insert = DB.insertData(user, pass);
                            if (insert) {
                                //Registeration successful
                                LoginDBActivity.currentUser = user;
                                Toast.makeText(LoginActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this , "Registeration failed!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this , "User already exists!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this , "Password not matching!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void initWidgets() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signIn = (Button) findViewById(R.id.buttonsignIN);
        signUp = (Button) findViewById(R.id.buttonsignUP);
        DB = new DBHelper(this);
        DBC = new DBCHelper(this);
        DBT = new DBTHelper(this);
    }
}
