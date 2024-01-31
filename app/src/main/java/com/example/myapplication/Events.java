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
    public Events(){
    }
    @NonNull
    @Override
    public String toString(){
        return sectionName + " - " + CalendarUtils.timeFormattor(timePicker)
                + "\n on " + datePickerButton.getDayOfWeek().toString();
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
