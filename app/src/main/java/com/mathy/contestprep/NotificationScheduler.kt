package com.mathy.contestprep

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock

class NotificationScheduler(private val context: Context) {

    fun scheduleDailyNotification() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Fix for Android 12+
        )
        context.sendBroadcast(intent)

        val triggerTime = SystemClock.elapsedRealtime() + (24 * 60 * 60 * 1000L)
        val intervalTime = 24 * 60 * 60 * 1000L

        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                intervalTime,
                pendingIntent
        )
    }
}