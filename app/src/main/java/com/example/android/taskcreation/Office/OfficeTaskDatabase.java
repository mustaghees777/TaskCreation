package com.example.android.taskcreation.Office;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {OfficeTask.class}, version = 1)
public abstract class OfficeTaskDatabase extends RoomDatabase {
    private static OfficeTaskDatabase instance;

    public abstract OfficeTaskDao tasktwoDao();

    public static synchronized OfficeTaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            OfficeTaskDatabase.class, "task_Two_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
