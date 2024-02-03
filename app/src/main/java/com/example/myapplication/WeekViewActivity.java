package com.example.myapplication;

import static com.example.myapplication.CalendarUtils.daysInMonthArray;
import static com.example.myapplication.CalendarUtils.daysInWeekArray;
import static com.example.myapplication.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R.id;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalenderAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    public static int position;
    private ListView listView;
    private Button viewCourse;
    DBCHelper DBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
        DBC = new DBCHelper(this);

    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
        CalenderAdapter calenderAdapter = new CalenderAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calenderAdapter);
        setEventAdapter();

        viewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.all_courses, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                TextView aa = popupView.findViewById(R.id.allCoursesText);
                String answer = "";
                for (int i = 0; i < Events.eventsList.size(); i++) {
                    answer += Events.eventsList.get(i).toString() + "\n";
                }
                aa.setText(answer);

                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }




    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calenderRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
        viewCourse = findViewById(R.id.viewCourses);
    }

    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    @Override
    public void onItemClick(int position, LocalDate date) {

           CalendarUtils.selectedDate = date;
           setWeekView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Events> dailyEvents = Events.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Events selectedClass = dailyEvents.get(position);
                String eventTitle = selectedClass.getEventNameET() +" - "+ CalendarUtils.timeFormattor(selectedClass.getTimePicker());
                eventTitle += "\n" + selectedClass.getInstructorName() +" - " + selectedClass.getLocationName() +" - " + selectedClass.getSectionName();
                eventTitle += "\n" + (selectedClass.repeatedDays());


                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.class_detail_view, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                TextView cc = popupView.findViewById(R.id.classDetailsText);
                cc.setText(eventTitle);

                Button dd = popupView.findViewById(R.id.deleteCourse);
                Button xx =popupView.findViewById(R.id.editCourse);


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
                xx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // inflate the layout of the popup window
                        LayoutInflater inflater = (LayoutInflater)
                                getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupVieww = inflater.inflate(R.layout.course_edit_confirm, null);

                        // create the popup window
                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText newCourseName = popupVieww.findViewById(R.id.editCourseName);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button deleteButt = popupVieww.findViewById(R.id.editConfirm);


                        boolean focusable = true; // lets taps outside the popup also dismiss it
                        final PopupWindow popupWindoww = new PopupWindow(popupVieww, width, height, focusable);

                        // show the popup window
                        // which view you pass in doesn't matter, it is only used for the window tolken
                        popupWindoww.showAtLocation(view, Gravity.CENTER, 0, 0);

                        // dismiss the popup window when touched
                        popupVieww.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                popupWindoww.dismiss();
                                return true;
                            }
                        });
                        deleteButt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DBC.updateCourse(LoginDBActivity.currentUser, selectedClass.getLocationName(),
                                        selectedClass.getSectionName(), selectedClass.getInstructorName(), newCourseName.getText().toString());

                                selectedClass.setEventNameET(newCourseName.getText().toString());
                                popupWindoww.dismiss();
                                setWeekView();
                                popupWindow.dismiss();


                            }
                        });
                    }
                });
                dd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // inflate the layout of the popup window
                        LayoutInflater inflater = (LayoutInflater)
                                getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupVieww = inflater.inflate(R.layout.course_confirmation, null);

                        // create the popup window
                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button deleteButt = popupVieww.findViewById(R.id.deleteConfirm);


                        boolean focusable = true; // lets taps outside the popup also dismiss it
                        final PopupWindow popupWindoww = new PopupWindow(popupVieww, width, height, focusable);

                        // show the popup window
                        // which view you pass in doesn't matter, it is only used for the window tolken
                        popupWindoww.showAtLocation(view, Gravity.CENTER, 0, 0);

                        // dismiss the popup window when touched
                        popupVieww.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                popupWindoww.dismiss();
                                return true;
                            }
                        });

                        deleteButt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DBC.deleteCourse(LoginDBActivity.currentUser, selectedClass.getLocationName(), selectedClass.getSectionName(),
                                        selectedClass.getInstructorName(), selectedClass.getEventNameET());
                                Events.eventsList.remove(selectedClass);


                                setWeekView();
                                popupWindoww.dismiss();
                                popupWindow.dismiss();;
                                }
                        });


                    }
                });
            }
        });
    }



    public void monthlyAction(View view) {

        startActivity(new Intent(this, MainActivity.class));
    }


    public void todoActions(View view) {
        startActivity(new Intent(this, TodoActivity.class));
    }
}