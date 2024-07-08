package com.example.android.taskcreation.Uni;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import com.example.android.taskcreation.R;
import com.example.android.taskcreation.TaskReminderReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

public class AddUniTaskActivtiy extends AppCompatActivity {
    private EditText editTextTaskName, editTextTaskDetail, editTextDueDate;
    private AppCompatButton submitButton;
    private Switch switchReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_form);

        editTextTaskName = findViewById(R.id.EdittextCardName);
        editTextTaskDetail = findViewById(R.id.EdittextCardNumber);
        editTextDueDate = findViewById(R.id.EdittextValidThruDate);
        submitButton = findViewById(R.id.submitButton);
        switchReminder = findViewById(R.id.switchReminder);

        switchReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
        editTextDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddUniTaskActivtiy.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDueDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


    private void saveTask() {
        final String taskName = editTextTaskName.getText().toString().trim();
        final String taskDetail = editTextTaskDetail.getText().toString().trim();
        final String dueDate = editTextDueDate.getText().toString().trim();

        final UniTask task = new UniTask();
        task.setTaskName(taskName);
        task.setTaskDetail(taskDetail);
        task.setDueDate(dueDate);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UniTaskDatabase db = UniTaskDatabase.getInstance(AddUniTaskActivtiy.this);
                db.taskOneDao().insert(task);

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(dueDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR_OF_DAY, 9); // Set the reminder time (e.g., 9 AM)
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);

                    long dueDateMillis = calendar.getTimeInMillis();

                    SharedPreferences prefs = getSharedPreferences("TASK_PREFS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("taskName", taskName);
                    editor.putString("taskDetail", taskDetail);
                    editor.putLong("dueDateMillis", dueDateMillis);
                    editor.apply();

                    scheduleNotification(taskName, taskDetail, dueDateMillis);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AddUniTaskActivtiy.this, UnibuttonActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private void scheduleNotification(String taskName, String taskDetail, long dueDateMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, TaskReminderReceiver.class);
        intent.putExtra("taskName", taskName);
        intent.putExtra("taskDetail", taskDetail);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dueDateMillis, alarmIntent);
    }
    }