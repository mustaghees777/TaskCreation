// TaskDatabase.java
package com.example.android.taskcreation.HomeActivity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HomeTask.class}, version = 1)
public abstract class HomeTaskDatabase extends RoomDatabase {
    private static HomeTaskDatabase instance;

    public abstract HomeTaskDao taskDao();

    public static synchronized HomeTaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            HomeTaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
