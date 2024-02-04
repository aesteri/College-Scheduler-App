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

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.buttonsignIN1);
        DB = new DBHelper(this);
        DBC = new DBCHelper(this);
        DBT = new DBTHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.equals("") ||pass.equals("")) {
                    Toast.makeText(LoginDBActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = DB.checkusernamePassword(user, pass);
                    if (checkuserpass) {
                        currentUser = user;


                        Cursor dataTask = DBT.showTasks(currentUser);
                        if (dataTask.getCount() == 0) {
                            //
                        } else {
                            while (dataTask.moveToNext()){
                                String name = dataTask.getString(1);
                                String course = dataTask.getString(2);
                                LocalDate date = LocalDate.parse(dataTask.getString(3));
                                boolean complete = Boolean.parseBoolean(dataTask.getString(4));
                                LocalTime time = LocalTime.parse(dataTask.getString(6));
                                String location = dataTask.getString(7);
                                //If task is exam...
                                if (Boolean.parseBoolean(dataTask.getString(5))) {
                                    Task.tasksList.add(new Exam(name, course, date, complete, time, location));
                                } else if (!Boolean.parseBoolean(dataTask.getString(5))){
                                    Task.tasksList.add(new Task(name, course, date, complete, time));
                                }
                            }
                        }
                        dataTask.close();


                        Cursor data = DBC.showCourses(currentUser);
                        if (data.getCount() == 0) {
                            //
                        } else {
                            while (data.moveToNext()){
                                LocalDate date = LocalDate.parse(data.getString(1));
                                LocalTime time = LocalTime.parse(data.getString(2));
                                String name = data.getString(3);
                                String instructor = data.getString(4);
                                String section = data.getString(5);
                                String location = data.getString(6);
                                boolean mon = Boolean.parseBoolean(data.getString(7));
                                boolean tue = Boolean.parseBoolean(data.getString(8));
                                boolean wed = Boolean.parseBoolean(data.getString(9));
                                boolean thur = Boolean.parseBoolean(data.getString(10));
                                boolean fri = Boolean.parseBoolean(data.getString(11));
                                Events.eventsList.add(new Events(date, time, name, instructor,section,location,mon,tue,wed,thur,fri));
                            }
                        }
                        data.close();


                        Toast.makeText(LoginDBActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);



                    } else {
                        Toast.makeText(LoginDBActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}