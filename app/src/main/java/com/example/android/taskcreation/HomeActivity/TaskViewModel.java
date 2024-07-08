// TaskViewModel.java
package com.example.android.taskcreation.HomeActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<HomeTask>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public void insert(HomeTask homeTask) {
        repository.insert(homeTask);
    }

    public void update(HomeTask homeTask) {
        repository.update(homeTask);
    }

    public void delete(HomeTask homeTask) {
        repository.delete(homeTask);
    }

    public LiveData<List<HomeTask>> getAllTasks() {
        return allTasks;
    }
}
