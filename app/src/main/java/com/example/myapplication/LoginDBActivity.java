package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;

public class LoginDBActivity extends AppCompatActivity {

    EditText username, password;
    Button btnlogin;
    DBHelper DB;
    DBCHelper DBC;
    DBTHelper DBT;
    public static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dbactivity);
        initWdigets();
        loginButtonClickListen();

    }
    private void loginButtonClickListen() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                //Make sure User enters all fields
                if (user.equals("") ||pass.equals("")) {
                    Toast.makeText(LoginDBActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = DB.checkusernamePassword(user, pass);
                    //If username and password are valid and in the system (User has registered before)
                    if (checkuserpass) {
                        //Set static variable so other classes can access current login user
                        currentUser = user;
                        //Appends saved data from current user to static list
                        //Curse through Database to see any saved Tasks from the user
                        Cursor dataTask = DBT.showTasks(currentUser);
                        if (dataTask.moveToFirst()) {
                            do {
                                //If task is exam...
                                if (Boolean.parseBoolean(dataTask.getString(5))) {
                                    //add Exam to tasksList
                                    Task.tasksList.add(new Exam(dataTask.getString(1), dataTask.getString(2),
                                            LocalDate.parse(dataTask.getString(3)), Boolean.parseBoolean(dataTask.getString(4)),
                                            LocalTime.parse(dataTask.getString(6)), dataTask.getString(7)));
                                } else if (!(Boolean.parseBoolean(dataTask.getString(5)))){
                                    //add Task instead
                                    Task.tasksList.add(new Task(dataTask.getString(1), dataTask.getString(2),
                                            LocalDate.parse(dataTask.getString(3)), Boolean.parseBoolean(dataTask.getString(4)),
                                            LocalTime.parse(dataTask.getString(6))));
                                }
                            } while (dataTask.moveToNext());
                        }
                        //Curse thur Database to see any saved events from user
                        Cursor data = DBC.showCourses(currentUser);
                        if (data.moveToFirst()) {
                            do {
                                //Add event from user database to static list
                                Events.eventsList.add(new Events(LocalDate.parse(data.getString(1)), LocalTime.parse(data.getString(2)),
                                        data.getString(3), data.getString(4), data.getString(5),
                                        data.getString(6), Boolean.parseBoolean(data.getString(7)),
                                        Boolean.parseBoolean(data.getString(8)), Boolean.parseBoolean(data.getString(9)),
                                        Boolean.parseBoolean(data.getString(10)), Boolean.parseBoolean(data.getString(11))));
                            } while (data.moveToNext());
                        }
                        //Indicate process was successful
                        Toast.makeText(LoginDBActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        //Password and username are not in system -- need to register
                        Toast.makeText(LoginDBActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //initiates necessary widgets
    private void initWdigets() {
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.buttonsignIN1);
        DB = new DBHelper(this);
        DBC = new DBCHelper(this);
        DBT = new DBTHelper(this);
    }
    //Button to go back to register screen
    public void GoBackRegister(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}