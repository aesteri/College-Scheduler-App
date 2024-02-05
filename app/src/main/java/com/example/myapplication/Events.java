package com.example.myapplication;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Events {
    public static ArrayList<Events> eventsList = new ArrayList<>();

    public static ArrayList<Events> eventsForDate(LocalDate date) {
        ArrayList<Events> events = new ArrayList<>();
        for (Events event : eventsList) {
            if (event.getDatePickerButton().equals(date) && !event.isFri()
                    &&!event.isThur()&&!event.isWed()&&!event.isTue()&&!event.isMon()) {
                events.add(event);
            }
            if (event.isFri() && date.getDayOfWeek().toString().equals("FRIDAY")) {
                events.add(event);
            }
            if (event.isThur() && date.getDayOfWeek().toString().equals("THURSDAY")) {
                events.add(event);
            }
            if (event.isWed() && date.getDayOfWeek().toString().equals("WEDNESDAY")) {
                events.add(event);
            }
            if (event.isTue() && date.getDayOfWeek().toString().equals("TUESDAY")) {
                events.add(event);
            }
            if (event.isMon() && date.getDayOfWeek().toString().equals("MONDAY")) {
                events.add(event);
            }
        }
        return events;
    }

    public static ArrayList<Events> eventsForDateandTime(LocalDate date, LocalTime time) {
        ArrayList<Events> events = new ArrayList<>();
        for (Events event : eventsList) {
            int eventHour = event.timePicker.getHour();
            int cellHour = time.getHour();

            if (event.getDatePickerButton().equals(date) && eventHour == cellHour && !event.isFri()
                    &&!event.isThur()&&!event.isWed()&&!event.isTue()&&!event.isMon()) {
                events.add(event);
            }
            if (event.isFri() && eventHour == cellHour && date.getDayOfWeek().toString().equals("FRIDAY")) {
                events.add(event);
            }
            if (event.isThur() && eventHour == cellHour && date.getDayOfWeek().toString().equals("THURSDAY")) {
                events.add(event);
            }
            if (event.isWed() && eventHour == cellHour && date.getDayOfWeek().toString().equals("WEDNESDAY")) {
                events.add(event);
            }
            if (event.isTue() && eventHour == cellHour && date.getDayOfWeek().toString().equals("TUESDAY")) {
                events.add(event);
            }
            if (event.isMon() && eventHour == cellHour && date.getDayOfWeek().toString().equals("MONDAY")) {
                events.add(event);
            }
        }
        return events;
    }
    private LocalDate datePickerButton;
    private LocalTime timePicker;

    private String eventNameET, instructorName, sectionName, locationName;

    private boolean mon, tue, wed, thur, fri;

    public Events(LocalDate datePickerButton, LocalTime timePicker,String eventNameET,
                  String instructorName, String sectionName, String locationName, boolean mon,boolean tue
    ,boolean wed, boolean thur,boolean fri) {
        this.datePickerButton = datePickerButton;
        this.timePicker = timePicker;
        this.eventNameET = eventNameET;
        this.instructorName = instructorName;
        this.sectionName = sectionName;
        this.locationName = locationName;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thur = thur;
        this.fri = fri;
    }
    @NonNull
    @Override
    public String toString(){
        return eventNameET + " - " + CalendarUtils.timeFormattor(timePicker)
                + "\n on " + datePickerButton.getDayOfWeek().toString();
    }
    public boolean isFri() {
        return fri;
    }

    public boolean isThur() {
        return thur;
    }

    public boolean isWed() {
        return wed;
    }

    public boolean isTue() {
        return tue;
    }

    public boolean isMon() {
        return mon;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getEventNameET() {
        return eventNameET;
    }

    public void setEventNameET(String eventNameET) {
        this.eventNameET = eventNameET;
    }

    public LocalTime getTimePicker() {
        return timePicker;
    }

    public LocalDate getDatePickerButton() {
        return datePickerButton;
    }

    public String repeatedDays(){
        String answer ="";
        if (isMon()) {
            answer+= "MON \n";
        } else if (isTue()){
            answer+= "TUE \n";
        } else if (isWed()) {
            answer+="WED \n";
        } else if (isThur()) {
            answer+="THUR \n";
        } else if (isFri()) {
            answer+="FRI";
        }
        return answer;
    }
}
