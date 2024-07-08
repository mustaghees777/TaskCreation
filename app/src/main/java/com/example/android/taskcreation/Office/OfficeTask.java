package com.example.android.taskcreation.Office;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_two")
public class OfficeTask {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String taskName;
    private String taskDetail;
    private String dueDate;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getTaskDetail() { return taskDetail; }
    public void setTaskDetail(String taskDetail) { this.taskDetail = taskDetail; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}
