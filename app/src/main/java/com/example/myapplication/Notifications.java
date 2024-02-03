//package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.AlarmManager;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.Toast;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZoneOffset;
//
//public class Notifications extends AppCompatActivity {
//    private CheckBox fiveMin, tenMin, fiftMin, twenMin, twnfiveMin, thirMin, fourfiveMin, onehr, twohr, threehr, onedy, twody;
//    private CheckBox task,assignment,exam, course;
//
//    public static String title;
//    public static String subtitle;
//    boolean five = false;
//    private Button back;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notifications);
//        buttonsAll();
//        createNotificationChannel();
//        initWidgets();
//    }
//
//    public void initWidgets(){
//        CheckBox fiveMin = findViewById(R.id.fiveMin);
//        CheckBox tenMin = findViewById(R.id.tenmin);
//        CheckBox fiftMin = findViewById(R.id.fiftemin);
//        CheckBox twenMin = findViewById(R.id.twenmin);
//        CheckBox twnfiveMin = findViewById(R.id.twenfivmin);
//        CheckBox thirMin = findViewById(R.id.thirmin);
//        CheckBox fourfiveMin = findViewById(R.id.fourfivmin);
//        CheckBox onehr = findViewById(R.id.onehr);
//        CheckBox twohr = findViewById(R.id.twohr);
//        CheckBox threehr = findViewById(R.id.thrhr);
//        CheckBox onedy = findViewById(R.id.onedy);
//        Button back= findViewById(R.id.backButton);
//        CheckBox twody = findViewById(R.id.twody);
//        CheckBox task = findViewById(R.id.taskNotif);
//        CheckBox assignment = findViewById(R.id.assignNotif);
//        CheckBox exam = findViewById(R.id.examNotif);
//        CheckBox course = findViewById(R.id.classNotif);
//    }
//
//    private void buttonsAll(){
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (fiveMin.isChecked()) {
//                    sendAlarm((long) 1000*10, "five minutes");
//                }
//                if (tenMin.isChecked()) {
//                    sendAlarm((long) 1000*30*2, "ten minutes");
//                }
//                if (fiftMin.isChecked()) {
//                    sendAlarm((long) 1000*30*3, "fifteen minutes");
//                }
//                if (twenMin.isChecked()) {
//                    sendAlarm((long) 1000*30*4, "twenty minutes");
//                }
//                if (twnfiveMin.isChecked()) {
//                    sendAlarm((long) 1000*30*5, "twenty five minutes");
//                }
//                if (thirMin.isChecked()) {
//                    sendAlarm((long) 1000*30*6, "thirty minutes");
//                }
//                if (fourfiveMin.isChecked()) {
//                    sendAlarm((long) 1000*30*8, "forty five minutes");
//                }if (onehr.isChecked()) {
//                    sendAlarm((long) 1000*30*11, "one hour");
//                }if (twohr.isChecked()) {
//                    sendAlarm((long) 1000*30*11*2, "two hours");
//                }
//                if (threehr.isChecked()) {
//                    sendAlarm((long) 1000*30*11*3, "three hours");
//                }
//                if (onedy.isChecked()) {
//                    sendAlarm((long) 1000*30*11*24, "one day");
//                }if (twody.isChecked()) {
//                    sendAlarm((long) 1000*30*11*24*2, "two days");
//                }
//
//
//                startActivity(new Intent(Notifications.this, MainActivity.class));
//
//            }
//        });
//    }
//
//    private void sendAlarm(long minu, String before) {
//        Toast.makeText(Notifications.this, "Reminder"+before+"before Set!", Toast.LENGTH_SHORT).show();
//        if (course.isChecked()) {
//            for (int i = 0; i < Events.eventsList.size(); i++) {
//                title = Events.eventsList.get(i).getEventNameET();
//                subtitle = "Class is in five minutes!";
//                Intent intent = new Intent(Notifications.this, ReminderBroadcaster.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(Notifications.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                LocalDateTime a = LocalDateTime.of(Events.eventsList.get(i).getDatePickerButton(), Events.eventsList.get(i).getTimePicker());
//                Instant instant = a.atZone(ZoneId.systemDefault()).toInstant();
//                Date date = Date.from(instant);
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                long min = minu;
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - min, pendingIntent);
//            }
//        }
//        if (task.isChecked()) {
//            for (int i = 0; i < Task.tasksList.size(); i++) {
//                title = Task.tasksList.get(i).getName();
//                subtitle = "Task is due in "+before+"!";
//                Intent intent = new Intent(Notifications.this, ReminderBroadcaster.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(Notifications.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                LocalDateTime a = LocalDateTime.of(Task.tasksList.get(i).getDuedate(), Task.tasksList.get(i).getTime());
//                Instant instant = a.atZone(ZoneId.systemDefault()).toInstant();
//                Date date = Date.from(instant);
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                long min = minu;
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - min, pendingIntent);
//                if (!(assignment.isChecked())) {
//                    if (!Task.tasksList.get(i).isExam()) {
//                        pendingIntent.cancel();
//                    }
//                }
//                if (!(exam.isChecked())) {
//                    if (Task.tasksList.get(i).isExam()) {
//                        pendingIntent.cancel();
//                    }
//                }
//
//            }
//        }
//
//    }
//
//    private void createNotificationChannel(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "CollegeSchedulerAppChannel";
//            String description ="Channel for Reminders";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("notify", name, importance);
//            channel.setDescription(description);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//
//
//        }
//    }
//
//}