package com.example.myapplication;

import static com.example.myapplication.R.id.sortCOMPLETE;
import static com.example.myapplication.R.id.taskDetailsTextt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TodoActivity extends AppCompatActivity{
    private ListView listview;
    private CheckBox checkBox;
    private TextView mainTask;

    private boolean coursess = false;
    private boolean dudates = true;
    private boolean complition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        initWidgets();
        setTaskAdapter();

        Task.tasksList.add(new Task("Exam 1", "CS1332", LocalDate.of(2024, 2,11), false));
        Task.tasksList.add(new Task("Exam 2", "CS2340", LocalDate.of(2024, 2,10), false));
        Task.tasksList.add(new Task("Homework 1", "CS2050", LocalDate.of(2024, 2,13), false));


    }

    @Override
    protected void onResume() {
        super.onResume();
        setTaskAdapter();
    }
    private void setTaskAdapter() {
        if (coursess) {
            sortbyCourse(Task.tasksList);
        } else if (dudates) {
            sortbyDueDate(Task.tasksList);
        } else if (complition) {
            sortbyComplete(Task.tasksList);
        }
        ArrayList<Task> arr = Task.tasksList;
        TodoAdapter todoAdapter = new TodoAdapter(getApplicationContext(), arr);
        listview.setAdapter(todoAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = arr.get(position);
                String descript = selectedTask.getCourse() + selectedTask.getName()
                        + "\n Due:" +selectedTask.getDuedate().toString();


                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.task_detail, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                TextView cc = popupView.findViewById(taskDetailsTextt);
                CheckBox checkBox1 = popupView.findViewById(R.id.checkboxTask);
                if (selectedTask.isComplete()) {
                    checkBox1.setChecked(true);
                }
                cc.setText(descript);
                Button edit =popupView.findViewById(R.id.editTask);

                Button dd = popupView.findViewById(R.id.deleteTask);


                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            selectedTask.setComplete(true);
                            setTaskAdapter();
                            popupWindow.dismiss();
                        } else {
                            selectedTask.setComplete(false);
                            setTaskAdapter();
                            popupWindow.dismiss();
                        }
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // inflate the layout of the popup window
                        LayoutInflater inflater = (LayoutInflater)
                                getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupVieww = inflater.inflate(R.layout.task_edit, null);

                        // create the popup window
                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText newCourseName = popupVieww.findViewById(R.id.editTaskNameET);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button deleteButt = popupVieww.findViewById(R.id.editTaskConfirm);


                        boolean focusable = true; // lets taps outside the popup also dismiss it
                        final PopupWindow popupWindoww = new PopupWindow(popupVieww, width, height, focusable);

                        // show the popup window
                        // which view you pass in doesn't matter, it is only used for the window tolken
                        popupWindoww.showAtLocation(view, Gravity.CENTER, 0, 0);

                        // dismiss the popup window when touched
                        popupVieww.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                popupWindoww.dismiss();
                                return true;
                            }
                        });
                        deleteButt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectedTask.setName(newCourseName.getText().toString());
                                setTaskAdapter();
                                popupWindoww.dismiss();
                                popupWindow.dismiss();

                            }
                        });
                    }
                });
                dd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            // inflate the layout of the popup window
                            LayoutInflater inflater = (LayoutInflater)
                                    getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupVieww = inflater.inflate(R.layout.task_delete_confirmation, null);

                            // create the popup window
                            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button deleteButt = popupVieww.findViewById(R.id.deleteTaskConfirm);


                            boolean focusable = true; // lets taps outside the popup also dismiss it
                            final PopupWindow popupWindoww = new PopupWindow(popupVieww, width, height, focusable);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            popupWindoww.showAtLocation(view, Gravity.CENTER, 0, 0);

                            // dismiss the popup window when touched
                            popupVieww.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    popupWindoww.dismiss();
                                    return true;
                                }
                            });

                            deleteButt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Task.tasksList.remove(selectedTask);
                                    popupWindoww.dismiss();
                                    popupWindow.dismiss();;
                                    setTaskAdapter();
                                }
                            });
                    }
                });


            }
        });
    }



    private void initWidgets() {
        listview = findViewById(R.id.tasksListView);

    }


    public void createAction(View view) {
        startActivity(new Intent(this, todoEdit.class));
    }


    public void sortAction(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupVieww = inflater.inflate(R.layout.sort_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        Button courses = popupVieww.findViewById(R.id.sortCourses);
        Button dueDate = popupVieww.findViewById(R.id.sortDates);
        Button complete = popupVieww.findViewById(R.id.sortCOMPLETE);

        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupVieww, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupVieww.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dudates = false;
                complition = false;
                coursess = true;
                setTaskAdapter();
                popupWindow.dismiss();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dudates = false;
                complition = true;
                coursess = false;
                setTaskAdapter();
                popupWindow.dismiss();
            }
        });
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dudates = true;
                complition = false;
                coursess = false;
                setTaskAdapter();
                popupWindow.dismiss();
            }
        });

    }

    private void sortbyCourse(ArrayList<Task> tasksList) {
        Collections.sort(tasksList,new Comparator<Task>() {
            @Override
            public int compare(Task s1, Task s2) {
                return s1.getCourse().compareToIgnoreCase(s2.getCourse());
            }
        });
    }

    private void sortbyDueDate(ArrayList<Task> tasksList) {
        Collections.sort(tasksList,new Comparator<Task>() {
            @Override
            public int compare(Task s1, Task s2) {
                return s1.getDuedate().toString().compareTo(s2.getDuedate().toString());
            }
        });
    }

    private void sortbyComplete(ArrayList<Task> tasksList) {
        Collections.sort(tasksList,new Comparator<Task>() {
            @Override
            public int compare(Task s1, Task s2) {
                return Boolean.compare(s1.isComplete(),s2.isComplete());
            }
        });
    }
}
