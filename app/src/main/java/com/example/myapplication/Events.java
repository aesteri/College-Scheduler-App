package com.example.myapplication;

import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Events {
    public static ArrayList<Events> eventsList = new ArrayList<>();

    public static ArrayList<Events> eventsForDate(LocalDate date) {
        ArrayList<Events> events = new ArrayList<>();
        for (Events event : eventsList) {
            if (event.getDatePickerButton().equals(date)) {
                events.add(event);
            }
        }
        return events;
    }
    private LocalDate datePickerButton;
    private LocalTime timePicker;

    private String eventNameET;
    private String instructorName;
    private String sectionName;
    private String locationName;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thur;
    private boolean fri;

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

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isThur() {
        return thur;
    }

    public void setThur(boolean thur) {
        this.thur = thur;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
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

    public void setTimePicker(LocalTime timePicker) {
        this.timePicker = timePicker;
    }

    public LocalDate getDatePickerButton() {
        return datePickerButton;
    }

    public void setDatePickerButton(LocalDate datePickerButton) {
        this.datePickerButton = datePickerButton;
    }


}
