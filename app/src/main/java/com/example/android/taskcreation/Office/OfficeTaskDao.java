package com.example.android.taskcreation.Office;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OfficeTaskDao {
    @Insert
    void insert(OfficeTask task);

    @Update
    void update(OfficeTask task);

    @Delete
    void delete(OfficeTask task);

    @Query("SELECT * FROM tasks_two")
    List<OfficeTask> getAllTasks();

    @Query("SELECT * FROM tasks_two WHERE id = :taskId")
    OfficeTask getTaskById(int taskId);
}
