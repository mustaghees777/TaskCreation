package com.example.android.taskcreation.Uni;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UniTask.class}, version = 1)
public abstract class UniTaskDatabase extends RoomDatabase {
    private static UniTaskDatabase instance;

    public abstract UniTaskDao taskOneDao();

    public static synchronized UniTaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UniTaskDatabase.class, "task_one_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
