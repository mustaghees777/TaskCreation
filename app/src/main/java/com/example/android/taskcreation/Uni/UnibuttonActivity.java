// HomebuttonActivity.java
package com.example.android.taskcreation.Uni;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskcreation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executors;

public class UnibuttonActivity extends AppCompatActivity implements UniTaskAdapter.OnTaskOneClickListener {
    private RecyclerView recyclerView;
    FloatingActionButton addbutton;
    private UniTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmaker);

        recyclerView = findViewById(R.id.listrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addbutton = findViewById(R.id.addbutton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnibuttonActivity.this, AddUniTaskActivtiy.class);
                startActivity(intent);
                finish();
            }
        });


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UniTaskDatabase db = UniTaskDatabase.getInstance(UnibuttonActivity.this);
                final List<UniTask> taskList = db.taskOneDao().getAllTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new UniTaskAdapter(taskList, UnibuttonActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    public void onTaskOneClick(final UniTask task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option")
                .setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Edit task
                            Intent intent = new Intent(UnibuttonActivity.this, EditUniTaskActivity.class);
                            intent.putExtra("taskId", task.getId());
                            startActivity(intent);
                        } else if (which == 1) {
                            // Delete task
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    UniTaskDatabase db = UniTaskDatabase.getInstance(UnibuttonActivity.this);
                                    db.taskOneDao().delete(task);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.removeTask(task);
                                            Toast.makeText(UnibuttonActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }
                })
                .show();
    }
}

