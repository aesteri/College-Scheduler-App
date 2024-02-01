package com.example.myapplication;

import java.time.LocalDate;
import java.util.ArrayList;

public class Task {
    private String name;

    private String course;

    private LocalDate duedate;
    private boolean complete;

    private boolean isExam;

    public static ArrayList<Task> tasksList = new ArrayList<>();

    public Task(String name, String course, LocalDate duedate, boolean complete) {
        this.name = name;
        this.duedate = duedate;
        this.course = course;
        this.complete = complete;
    }

    public Task(){
        this(null,null, null, false);
    }

    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {

    }
}
