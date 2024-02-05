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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class todoEdit extends AppCompatActivity {
    private int min, hour;
    private DatePickerDialog datePickerDialog;
    private LocalTime TIME;

    private EditText name, location, course;
    private Button dateButton, timeButton;
    private CheckBox isExam;
    private LocalDate DATE;
    DBTHelper DBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_create_edit);
        initDatePicker();
        initWidgets();
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.TimePickerDialogTheme, onTimeSetListener,hour, min, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    private void initWidgets(){
        dateButton = findViewById(R.id.dateTaskButton);
        dateButton.setText(getTodaysDate());
        DATE = CalendarUtils.selectedDate;
        timeButton = findViewById(R.id.timeTaskButton);
        name = findViewById(R.id.taskNameET);
        location = findViewById(R.id.locationTask);
        course = findViewById(R.id.courseTask);
        isExam = findViewById(R.id.checkboxisExam);
        DBT = new DBTHelper(this);
    }

    public void saveTask(View view) {
        if (TIME == null || name.getText().toString() == "" || course.getText().toString()== "") {
            Toast.makeText(todoEdit.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
        } else {
            if (isExam.isChecked()) {
                DBT.insertTask(LoginDBActivity.currentUser, name.getText().toString(), course.getText().toString(), DATE,false, true,TIME, location.getText().toString());
                Exam exam = new Exam(name.getText().toString(), course.getText().toString(), DATE, false, TIME, location.getText().toString());
                Task.tasksList.add(exam);
            } else {
                DBT.insertTask(LoginDBActivity.currentUser, name.getText().toString(), course.getText().toString(),DATE,false, false,TIME, "");
                Task newTask = new Task(name.getText().toString(), course.getText().toString(), DATE, false, TIME);
                Task.tasksList.add(newTask);
            }
            finish();
        }
    }

    public void backToACtion(View view) {
        finish();
    }
}
