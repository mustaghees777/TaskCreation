// HomebuttonActivity.java
package com.example.android.taskcreation.HomeActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskcreation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomebuttonActivity extends AppCompatActivity implements HomeTaskAdapter.OnTaskListener {

    private static final int ADD_TASK_REQUEST = 1;

    private FloatingActionButton addbutton;
    private RecyclerView listrecycler;
    private HomeTaskAdapter homeTaskAdapter;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.listmaker);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listrecycler = findViewById(R.id.listrecycler);
        addbutton = findViewById(R.id.addbutton);

        // Initialize task list and adapter
        homeTaskAdapter = new HomeTaskAdapter(this);
        listrecycler.setLayoutManager(new LinearLayoutManager(this));
        listrecycler.setAdapter(homeTaskAdapter);

        // ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<HomeTask>>() {
            @Override
            public void onChanged(List<HomeTask> homeTasks) {
                homeTaskAdapter.setTasks(homeTasks);
            }
        });

        addbutton.setOnClickListener(v -> {
            Intent i = new Intent(this, AddHomeTaskActivity.class);
            startActivityForResult(i, ADD_TASK_REQUEST);
        });
    }

    @Override
    public void onTaskClick(int position) {
        HomeTask selectedHomeTask = homeTaskAdapter.getTaskAt(position);

        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an action")
                .setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Edit action
                                Intent intent = new Intent(HomebuttonActivity.this, AddHomeTaskActivity.class);
                                intent.putExtra("task", selectedHomeTask);
                                startActivity(intent);
                                break;
                            case 1:
                                // Delete action
                                taskViewModel.delete(selectedHomeTask);
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK && data != null) {
            // Handle any additional logic if needed
        }
    }
}
