// Task.java
package com.example.android.taskcreation.HomeActivity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tasks")
public class HomeTask implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String details;
    private String DueDate;

    public HomeTask(String name, String details, String DueDate) {
        this.name = name;
        this.details = details;
        this.DueDate = DueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }
}
