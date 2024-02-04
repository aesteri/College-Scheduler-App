package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<Task> {
    public TodoAdapter(@NonNull Context context, List<Task> tasks){
        super(context,0, tasks);

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_cell, parent, false);
        }
        TextView taskCell = convertView.findViewById(R.id.taskCell);
        String taskTitle = task.getDuedate().toString() + " " + task.getName() + " " + task.getCourse();
        taskCell.setText(taskTitle);
        if (task.isComplete()) {
            taskCell.setVisibility(taskCell.GONE);
            taskCell.setBackgroundColor(Color.GREEN);
        }
        if (!task.isComplete()) {
            taskCell.setVisibility(taskCell.VISIBLE);
            taskCell.setBackgroundColor(Color.LTGRAY);
        }
        if (task instanceof Exam) {
            taskCell.setBackgroundColor(Color.RED);
        }

        return convertView;
    }
}
