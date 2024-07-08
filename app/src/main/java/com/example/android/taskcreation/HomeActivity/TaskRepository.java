// TaskRepository.java
package com.example.android.taskcreation.HomeActivity;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private HomeTaskDao homeTaskDao;
    private LiveData<List<HomeTask>> allTasks;
    private ExecutorService executorService;

    public TaskRepository(Application application) {
        HomeTaskDatabase database = HomeTaskDatabase.getInstance(application);
        homeTaskDao = database.taskDao();
        allTasks = homeTaskDao.getAllTasks();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(HomeTask homeTask) {
        executorService.execute(() -> homeTaskDao.insert(homeTask));
    }

    public void update(HomeTask homeTask) {
        executorService.execute(() -> homeTaskDao.update(homeTask));
    }

    public void delete(HomeTask homeTask) {
        executorService.execute(() -> homeTaskDao.delete(homeTask));
    }

    public LiveData<List<HomeTask>> getAllTasks() {
        return allTasks;
    }
}
