package com.example.android.taskcreation.Office;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.android.taskcreation.R;
import com.example.android.taskcreation.Uni.UnibuttonActivity;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class EditTaskTwoActivity extends AppCompatActivity {
    private EditText editTextTaskName, editTextTaskDetail, editTextDueDate;
    private AppCompatButton updateButton;
    private OfficeTaskDatabase db;
    private OfficeTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_form);

        editTextTaskName = findViewById(R.id.EdittextCardName);
        editTextTaskDetail = findViewById(R.id.EdittextCardNumber);
        editTextDueDate = findViewById(R.id.EdittextValidThruDate);
        updateButton = findViewById(R.id.submitButton);

        db = OfficeTaskDatabase.getInstance(this);
        int taskId = getIntent().getIntExtra("taskId", -1);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                task = db.tasktwoDao().getTaskById(taskId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (task != null) {
                            editTextTaskName.setText(task.getTaskName());
                            editTextTaskDetail.setText(task.getTaskDetail());
                            editTextDueDate.setText(task.getDueDate());
                        }
                    }
                });
            }
        });

        updateButton.setBackgroundResource(R.drawable.updatebtn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskTwoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDueDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    private void updateTask() {
        final String taskName = editTextTaskName.getText().toString().trim();
        final String taskDetail = editTextTaskDetail.getText().toString().trim();
        final String dueDate = editTextDueDate.getText().toString().trim();

        task.setTaskName(taskName);
        task.setTaskDetail(taskDetail);
        task.setDueDate(dueDate);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.tasktwoDao().update(task);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(EditTaskTwoActivity.this, OfficebuttonActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}