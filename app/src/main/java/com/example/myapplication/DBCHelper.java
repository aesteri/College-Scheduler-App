package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DBCHelper extends SQLiteOpenHelper {
    //DB to store in Courses
    public static final String DBName = "Course.db";
    public DBCHelper(@Nullable Context context) {
        super(context, "Course.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase MyDBC) {
        MyDBC.execSQL("create Table courses(username TEXT, date TEXT, time TEXT, name TEXT, instructor TEXT" +
                ", section TEXT, location TEXT, mon TEXT, tue TEXT, wed TEXT, thur TEXT, fri TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDBC, int oldVersion, int newVersion) {
        MyDBC.execSQL("drop Table if exists courses");
    }
    public Boolean insertCourse(String username, LocalDate date, LocalTime time, String name,
                                String instructor, String section, String location,
                                Boolean mon, Boolean tue, Boolean wed, Boolean thur, Boolean fri) {
        SQLiteDatabase MyDBC = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("date", date.toString());
        contentValues.put("time", time.toString());
        contentValues.put("name", name);
        contentValues.put("instructor", instructor);
        contentValues.put("section", section);
        contentValues.put("location", location);
        contentValues.put("mon", mon.toString());
        contentValues.put("tue", tue.toString());
        contentValues.put("wed", wed.toString());
        contentValues.put("thur", thur.toString());
        contentValues.put("fri", fri.toString());
        long result = MyDBC.insert("courses", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor showCourses(String username){
        SQLiteDatabase MyDBC = this.getWritableDatabase();
        Cursor cursor = MyDBC.rawQuery("Select * from courses where username = ?", new String[] {username});
        return cursor;
    }
    public void updateCourse(String username, String location, String section, String instructor, String newname){
        SQLiteDatabase MyDBC = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newname);
        MyDBC.update("courses", contentValues,"username = ? and location = ? and section = ? and instructor = ?", new String[]{username, location,section,instructor});

    }

    public void deleteCourse(String username, String location, String section, String instructor, String name){
        SQLiteDatabase MyDBC = this.getWritableDatabase();
        MyDBC.delete("courses", "username = ? and location = ? and section = ? and instructor = ? and name = ?",
                new String[] {username, location, section, instructor, name});
    }


}
