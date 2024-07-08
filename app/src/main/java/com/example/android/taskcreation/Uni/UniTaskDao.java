package com.example.android.taskcreation.Uni;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UniTaskDao {
    @Insert
    void insert(UniTask task);

    @Update
    void update(UniTask task);

    @Delete
    void delete(UniTask task);

    @Query("SELECT * FROM tasks_one")
    List<UniTask> getAllTasks();

    @Query("SELECT * FROM tasks_one WHERE id = :taskId")
    UniTask getTaskById(int taskId);
}
