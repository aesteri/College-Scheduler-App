package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity{
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        initWidgets();
        setListView();
    }

    private void setListView() {
        ArrayList<String> arrayList = getTasksList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TodoActivity.this,android.R.layout.simple_list_item_multiple_choice, arrayList
        );
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = (String) parent.getItemAtPosition(position);
                Toast.makeText(TodoActivity.this,"selcted!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<String> getTasksList() {
        ArrayList<String> temp = new ArrayList<>();
        return temp;
    }

    private void initWidgets() {
        listview = findViewById(R.id.tasksListView);

    }
    public void returnBackToDo(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

    public void createAction(View view) {
        startActivity(new Intent(this, todoEdit.class));
    }


    public void sortAction(View view) {

    }
}
