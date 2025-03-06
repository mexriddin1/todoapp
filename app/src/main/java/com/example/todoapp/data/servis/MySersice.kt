package com.example.todoapp.data.servis

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.todoapp.R
import com.example.todoapp.data.local.room.entity.TaskUiData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@SuppressLint("NewApi", "ScheduleExactAlarm")
class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskTitle = intent.getStringExtra("TASK_TITLE") ?: "Task Reminder"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "TASK_REMINDER_CHANNEL"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Task Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Reminder")
            .setContentText("it is time to do $taskTitle")
            .setSmallIcon(R.drawable.other)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

@SuppressLint("NewApi", "ScheduleExactAlarm")
fun saveTaskAndScheduleAlarm(context: Context, todoEntity: TaskUiData) {
    if (todoEntity.alertTime != null) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskAlarmReceiver::class.java).apply {
            putExtra("TASK_TITLE", todoEntity.task)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            todoEntity.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val taskDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(todoEntity.alertTime),
            ZoneId.systemDefault()
        )
        val currentDateTime = LocalDateTime.now()

        var alarmDateTime = taskDateTime
        if (taskDateTime.isBefore(currentDateTime)) {
            alarmDateTime = taskDateTime.plusDays(1)
        }

        val alarmTimeMillis =
            alarmDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTimeMillis,
            pendingIntent
        )

    }
}


