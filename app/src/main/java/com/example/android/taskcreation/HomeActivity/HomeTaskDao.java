// TaskDao.java
package com.example.android.taskcreation.HomeActivity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HomeTaskDao {
    @Insert
    void insert(HomeTask homeTask);

    @Update
    void update(HomeTask homeTask);

    @Delete
    void delete(HomeTask homeTask);

    @Query("SELECT * FROM tasks")
    LiveData<List<HomeTask>> getAllTasks();
}
