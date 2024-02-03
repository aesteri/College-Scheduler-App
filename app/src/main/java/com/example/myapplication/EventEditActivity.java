package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private Button timeButton;
    private int min, hour;

    private EditText eventNameET;
    private EditText instructorName;
    private EditText sectionName;
    private EditText locationName;

    private RadioButton mon;
    private RadioButton tue;
    private RadioButton wed;
    private RadioButton thur;
    private RadioButton fri;

    private LocalDate DATEE;
    private LocalTime TIMEE;
    DBCHelper DBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        DATEE = CalendarUtils.selectedDate;
        timeButton = findViewById(R.id.timePicker);
        initWidgets();
        DBC = new DBCHelper(this);

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
                DATEE = LocalDate.of(year, month, dayOfMonth);
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
                TIMEE = LocalTime.of(hour,min);
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,hour, min, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void initWidgets(){
        eventNameET = findViewById(R.id.eventNameET);
        instructorName= findViewById(R.id.instructorName);
        sectionName= findViewById(R.id.sectionName);
        locationName= findViewById(R.id.locationName);
        mon = findViewById(R.id.monRepeat);
        tue = findViewById(R.id.tueRepeat);
        wed = findViewById(R.id.wedRepeat);
        thur = findViewById(R.id.thurRepeat);
        fri = findViewById(R.id.friRepeat);
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        String instruct = instructorName.getText().toString();
        String locat = locationName.getText().toString();
        String sect = sectionName.getText().toString();
        boolean monn = mon.isChecked();
        boolean tuee = tue.isChecked();
        boolean wedd = wed.isChecked();
        boolean thurr = thur.isChecked();
        boolean frii = fri.isChecked();

        DBC.insertCourse(LoginDBActivity.currentUser, DATEE, TIMEE, eventName, instruct, sect, locat,monn, tuee,wedd,thurr,frii);


        Events newEvent = new Events(DATEE, TIMEE, eventName, instruct, sect, locat,monn, tuee, wedd, thurr, frii );
        Events.eventsList.add(newEvent);
        finish();
    }

}