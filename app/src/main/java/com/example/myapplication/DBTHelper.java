package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

public class DBTHelper extends SQLiteOpenHelper {
    public static final String DBName = "Tasks.db";
    public DBTHelper(@Nullable Context context) {
        super(context, "Tasks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDBT) {
        MyDBT.execSQL("create Table tasks(username TEXT primary key, name TEXT, course TEXT, duedate TEXT, complete TEXT" +
                ", exam TEXT, time TEXT, location TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDBT, int oldVersion, int newVersion) {
        MyDBT.execSQL("drop Table if exists tasks");
    }

    public Boolean insertTask(String username, String name, String course, LocalDate duedate, Boolean complete, Boolean exam, LocalTime time, String location) {
        String d = duedate.toString();
        String examString = exam.toString();
        String t = time.toString();
        String completeString = complete.toString();
        SQLiteDatabase MyDBT = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("course", course);
        contentValues.put("duedate", d);
        contentValues.put("complete", completeString);
        contentValues.put("exam", examString);
        contentValues.put("time", t);
        contentValues.put("location", location);
        long result = MyDBT.insert("tasks", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor showTasks(String username){
        SQLiteDatabase MyDBT = this.getWritableDatabase();
        Cursor cursor = MyDBT.rawQuery("Select * from tasks where username = ?", new String[] {username});
        return cursor;
    }
    public void updateTask(String username, String newname, String name, String course){
        SQLiteDatabase MyDBT = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newname);
        MyDBT.update("tasks", contentValues,"username = ? and name = ? and course = ?", new String[]{username, name, course});

    }
    public void updateComplete(String username, String newcom, String name, String course){
        SQLiteDatabase MyDBT = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("complete", newcom);
        MyDBT.update("tasks", contentValues,"username = ? and name = ? and course = ?", new String[]{username, name, course});

    }

    public void deleteTask(String username, String name, String course, LocalDate duedate, Boolean exam){
        String d = duedate.toString();
        String examString = exam.toString();
        SQLiteDatabase MyDBT = this.getWritableDatabase();
        MyDBT.delete("tasks", "username = ? and name = ? and course = ? and duedate = ? and exam = ?",
                new String[] {username, name, course, d, examString});
    }
}
