package com.example.myapplication;

import static com.example.myapplication.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DailyViewActivity extends AppCompatActivity {
    //In chare of daily view
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
        setDayView();
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

    public void montlyAction(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void viewCourseAction(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.all_courses, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        TextView allCoursesTxt = popupView.findViewById(R.id.allCoursesText);
        String answer = "";
        for (int i = 0; i < Events.eventsList.size(); i++) {
            answer += "\n"+Events.eventsList.get(i).toString() + "\n";
        }
        allCoursesTxt.setText(answer);
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}