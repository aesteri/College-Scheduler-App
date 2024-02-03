package com.example.myapplication;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exam extends Task{
    private LocalTime time;
    private String location;

    public Exam(String name, String course, LocalDate duedate, boolean complete, LocalTime time, String location){
        super(name,course,duedate,complete, false);
        this.time = time;
        this.location = location;
    }
    public Exam() {
    }
}
