package com.example.myapplication;

import static com.example.myapplication.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DailyViewActivity extends AppCompatActivity {
    private TextView dayYearTV;
    private TextView dayOfWeekTV;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    public static int position;
    private ListView listView;
    private Button viewCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view2);
        initWidgets();
    }

    private void initWidgets() {
        dayYearTV = findViewById(R.id.dayYearTV);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        eventListView = findViewById(R.id.eventListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayView();
        
    }

    private void setDayView() {
        dayYearTV.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        eventListView.setAdapter(hourAdapter);
    }

    private ArrayList<hourEvent> hourEventList() {
        ArrayList<hourEvent> list  = new ArrayList<>();
        for(int hour = 0; hour < 24; hour++){
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Events> events = Events.eventsForDateandTime(selectedDate, time);
            hourEvent HourEvent = new hourEvent(time, events);
            list.add(HourEvent);
        }
        return list;
    }

    public void previousDayAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }
    public void nextDayAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }
    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }
    public void todoAction(View view) {
        startActivity(new Intent(this, TodoActivity.class));
    }
}