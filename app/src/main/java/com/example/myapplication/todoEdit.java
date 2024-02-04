package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class todoEdit extends AppCompatActivity {
    private int min, hour;
    private DatePickerDialog datePickerDialog;
    private LocalTime TIME;

    private EditText name;
    private EditText location;
    private EditText course;
    private LocalDate date;
    private LocalTime time;
    private Button dateButton;
    private CheckBox isExam;
    private LocalDate DATE;
    private Button timeButton;
    DBTHelper DBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_create_edit);

        initDatePicker();
        dateButton = findViewById(R.id.dateTaskButton);
        dateButton.setText(getTodaysDate());
        DATE = CalendarUtils.selectedDate;
        timeButton = findViewById(R.id.timeTaskButton);
        initWidgets();

        DBT = new DBTHelper(this);
    }

    private String getTodaysDate() {
        LocalDate selected = CalendarUtils.selectedDate;
        int year = selected.getYear();
        String month = selected.getMonth().toString();
        int day = selected.getDayOfMonth();
        String ans = month + " " + day + " " + year;
        return ans;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                DATE = LocalDate.of(year, month, dayOfMonth);
                dateButton.setText(date);

            }
        };
        LocalDate selected = CalendarUtils.selectedDate;
        int year = selected.getYear();
        int month = selected.getMonth().getValue() - 1;
        int day = selected.getDayOfMonth();
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
    private String makeDateString(int dayOfMonth, int month,int year) {
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "";
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                TIME = LocalTime.of(hour,min);
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,hour, min, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    private void initWidgets(){
        name = findViewById(R.id.taskNameET);
        location = findViewById(R.id.locationTask);
        course = findViewById(R.id.courseTask);
        isExam = findViewById(R.id.checkboxisExam);
    }

    public void saveTask(View view) {
        String taskName = name.getText().toString();
        String taskLocation = location.getText().toString();
        String taskCourse = course.getText().toString();
        if (isExam.isChecked()) {
            Exam exam = new Exam(taskName, taskCourse, DATE, false, TIME, taskLocation);
            Task.tasksList.add(exam);
            DBT.insertTask(LoginDBActivity.currentUser, taskName, taskCourse,DATE,false, true,TIME, taskLocation);
        } else {
            Task newTask = new Task(taskName, taskCourse, DATE, false, TIME);
            Task.tasksList.add(newTask);
            taskLocation = "";
            DBT.insertTask(LoginDBActivity.currentUser, taskName, taskCourse,DATE,false, false,TIME, taskLocation);
        }



        finish();
    }

}
