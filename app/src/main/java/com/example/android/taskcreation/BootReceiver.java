package com.example.android.taskcreation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            SharedPreferences prefs = context.getSharedPreferences("TASK_PREFS", Context.MODE_PRIVATE);
            String taskName = prefs.getString("taskName", null);
            String taskDetail = prefs.getString("taskDetail", null);
            long dueDateMillis = prefs.getLong("dueDateMillis", -1);

            if (taskName != null && taskDetail != null && dueDateMillis != -1) {
                scheduleNotification(context, taskName, taskDetail, dueDateMillis);
            }
        }
    }

    private void scheduleNotification(Context context, String taskName, String taskDetail, long dueDateMillis) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TaskReminderReceiver.class);
        intent.putExtra("taskName", taskName);
        intent.putExtra("taskDetail", taskDetail);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dueDateMillis, alarmIntent);
    }
}

