// AddTaskActivity.java
package com.example.android.taskcreation.HomeActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.taskcreation.Office.AddOfficeTaskActivity;
import com.example.android.taskcreation.R;

import java.util.Calendar;

public class AddHomeTaskActivity extends AppCompatActivity {
    private EditText editTextCardName, editTextCardNumber, editTextValidThruDate;
    private AppCompatButton submitButton;
    private TaskViewModel taskViewModel;
    private HomeTask homeTaskToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_form);

        // Initializing UI components
        editTextCardName = findViewById(R.id.EdittextCardName);
        editTextCardNumber = findViewById(R.id.EdittextCardNumber);
        editTextValidThruDate = findViewById(R.id.EdittextValidThruDate);
        submitButton = findViewById(R.id.submitButton);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        editTextValidThruDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        // Check if editing an existing task
        if (getIntent().hasExtra("task")) {
            homeTaskToEdit = (HomeTask) getIntent().getSerializableExtra("task");
            editTextCardName.setText(homeTaskToEdit.getName());
            editTextCardNumber.setText(homeTaskToEdit.getDetails());
            editTextValidThruDate.setText(homeTaskToEdit.getDueDate());
        }

        // Setting up click listener for the submit button
        submitButton.setOnClickListener(view -> {
            String cardName = editTextCardName.getText().toString();
            String cardNumber = editTextCardNumber.getText().toString();
            String validThruDate = editTextValidThruDate.getText().toString();

            // Validate input
            if (cardName.isEmpty() || cardNumber.isEmpty() || validThruDate.isEmpty()) {
                Toast.makeText(AddHomeTaskActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new task or update existing task
            if (homeTaskToEdit != null) {
                homeTaskToEdit.setName(cardName);
                homeTaskToEdit.setDetails(cardNumber);
                homeTaskToEdit.setDueDate(validThruDate);
                taskViewModel.update(homeTaskToEdit);
                Toast.makeText(AddHomeTaskActivity.this, "Task Updated", Toast.LENGTH_LONG).show();
            } else {
                HomeTask newHomeTask = new HomeTask(cardName, cardNumber, validThruDate);
                taskViewModel.insert(newHomeTask);
                Toast.makeText(AddHomeTaskActivity.this, "Task Added", Toast.LENGTH_LONG).show();
            }
            finish();
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddHomeTaskActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextValidThruDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
