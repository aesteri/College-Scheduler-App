package com.example.myapplication;

import java.time.LocalTime;
import java.util.ArrayList;

class hourEvent {
    LocalTime time;
    ArrayList<Events> events;

    public hourEvent(LocalTime time, ArrayList<Events> events) {
        this.time = time;
        this.events = events;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}