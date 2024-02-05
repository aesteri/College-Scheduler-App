package com.example.myapplication;

import static com.example.myapplication.R.id.taskDetailsTextt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TodoActivity extends AppCompatActivity{
    private ListView listview;
    private CheckBox checkBoxTask;
    private TextView taskDetailsTxt;
    private Button editTaskBtn, deleteTaskBtn, editTaskConfirmBtn, deleteTaskConfirmBtn;

    private boolean coursess = false;
    private boolean duedates = false;
    private boolean complition = true;
    private EditText editTaskConfirmTxt;
    private static boolean filters = true;
    private Task selectedTask;

    DBTHelper DBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        initWidgets();
        //Adapt Task to list out tasks
        setTaskAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTaskAdapter();
    }
    private void setTaskAdapter() {
        //determines how Tasks should be sorted by
        determineWhichSort();
        TodoAdapter todoAdapter = new TodoAdapter(getApplicationContext(), Task.tasksList);
        listview.setAdapter(todoAdapter);
        //Whenever you click on a task....
        setTodoListView();


    }
    private void setTodoListView() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Popup window will appear
                popupWillAppear(position);
            }
        });
    }

    private void popupWillAppear(int position) {
            //define selectedTask (the task user clicked on)
            selectedTask = Task.tasksList.get(position);
            //quick description of it
            String description = selectedTask.getCourse() + selectedTask.getName()
                + "\n Due:" + selectedTask.getDuedate().toString();
            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.task_detail, null);
            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            popupWidigets(popupView);
            taskDetailsTxt.setText(description);
            boolean focusable = true; // Tap outside then popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            // show the popup window
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
            //Create a checkbox listener
            checkBoxTaskListener(popupWindow);
            //Create a edit task listener
            editTaskBtnListener(popupWindow);
            //Create a delete task listener
            deleteTaskBtnListener(popupWindow);


        }

    private void deleteTaskBtnListener(PopupWindow popupWindow) {
        deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            //Popup will appear for delete confirmation
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupVieww = inflater.inflate(R.layout.task_delete_confirmation, null);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                deleteTaskConfirmBtn = popupVieww.findViewById(R.id.deleteTaskConfirm);
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindoww = new PopupWindow(popupVieww, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindoww.showAtLocation(popupVieww, Gravity.CENTER, 0, 0);
                // dismiss the popup window when touched
                popupVieww.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindoww.dismiss();
                        return true;
                    }
                });
                //Create a confirm button
                deleteTaskConfirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Remove task locally
                        Task.tasksList.remove(selectedTask);
                        //Delete task in DB (Database)
                        DBT.deleteTask(LoginDBActivity.currentUser, selectedTask.getName(), selectedTask.getCourse(),
                                selectedTask.getDuedate(), selectedTask instanceof Exam);
                        popupWindoww.dismiss();
                        popupWindow.dismiss();;
                        setTaskAdapter();
                    }
                });
            }
        });
    }

    private void editTaskBtnListener(PopupWindow popupWindow) {
        //Create a confirm popup window
        editTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupVieww = inflater.inflate(R.layout.task_edit, null);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                editTaskConfirmTxt = popupVieww.findViewById(R.id.editTaskNameET);
                editTaskConfirmBtn = popupVieww.findViewById(R.id.editTaskConfirm);
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindoww = new PopupWindow(popupVieww, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindoww.showAtLocation(popupVieww, Gravity.CENTER, 0, 0);
                // dismiss the popup window when touched
                popupVieww.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindoww.dismiss();
                        return true;
                    }
                });
                editTaskConfirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update Task accordingly (locally + DB)
                        selectedTask.setName(editTaskConfirmTxt.getText().toString());
                        DBT.updateTask(LoginDBActivity.currentUser, editTaskConfirmTxt.getText().toString(), selectedTask.getName(), selectedTask.getCourse());
                        setTaskAdapter();
                        popupWindoww.dismiss();
                        popupWindow.dismiss();

                    }
                });
            }
        });
    }

    private void checkBoxTaskListener(PopupWindow popupWindow) {
        //If checkbox is clicked...
        checkBoxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //update DB accordingly
                    selectedTask.setComplete(true);
                    setTaskAdapter();
                    DBT.updateComplete(LoginDBActivity.currentUser, "true", selectedTask.getName(), selectedTask.getCourse());
                    popupWindow.dismiss();
                } else {
                    selectedTask.setComplete(false);
                    DBT.updateComplete(LoginDBActivity.currentUser, "false", selectedTask.getName(), selectedTask.getCourse());
                    setTaskAdapter();
                    popupWindow.dismiss();
                }
            }
        });
    }

    private void popupWidigets(View popupView) {
        //Widget initializers
        taskDetailsTxt = popupView.findViewById(taskDetailsTextt);
        checkBoxTask = popupView.findViewById(R.id.checkboxTask);
        editTaskBtn = popupView.findViewById(R.id.editTask);
        deleteTaskBtn = popupView.findViewById(R.id.deleteTask);
        if (selectedTask.isComplete()) {
            checkBoxTask.setChecked(true);
        }
        if (selectedTask instanceof Exam) {
            checkBoxTask.setVisibility(checkBoxTask.GONE);
        } else {
            checkBoxTask.setVisibility(checkBoxTask.VISIBLE);
        }
    }
    private void determineWhichSort() {
        if (coursess) {
            sortbyCourse(Task.tasksList);
        } else if (duedates) {
            sortbyDueDate(Task.tasksList);
        } else if (complition) {
            sortbyComplete(Task.tasksList);
        }
    }
    private void initWidgets() {
        listview = findViewById(R.id.tasksListView);
        DBT = new DBTHelper(this);

    }
    public void createAction(View view) {
        startActivity(new Intent(this, todoEdit.class));
    }
    public void sortAction(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.sort_popup, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        Button courses = popupView.findViewById(R.id.sortCourses);
        Button dueDate = popupView.findViewById(R.id.sortDates);
        Button complete = popupView.findViewById(R.id.sortCOMPLETE);
        Button filterComplete = popupView.findViewById(R.id.filterComplete);
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duedates = false;
                complition = false;
                coursess = true;
                setTaskAdapter();
                popupWindow.dismiss();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duedates = false;
                complition = true;
                coursess = false;
                setTaskAdapter();
                popupWindow.dismiss();
            }
        });
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duedates = true;
                complition = false;
                coursess = false;
                setTaskAdapter();
                popupWindow.dismiss();
            }
        });

        filterComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filters = !filters;
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
        sortbyComplete(tasksList);
    }

    private void sortbyDueDate(ArrayList<Task> tasksList) {
        Collections.sort(tasksList, new Comparator<Task>() {
            @Override
            public int compare(Task s1, Task s2) {
                return s1.getDuedate().toString().compareTo(s2.getDuedate().toString());
            }
        });
        sortbyComplete(tasksList);
    }

    private void sortbyComplete(ArrayList<Task> tasksList) {
        Collections.sort(tasksList, new Comparator<Task>() {
            @Override
            public int compare(Task s1, Task s2) {
                return Boolean.compare(s1.isComplete(),s2.isComplete());
            }
        });
    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public static boolean getFilters() {
        return filters;
    }

    public void weekAA(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
}
