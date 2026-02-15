package com.mathy.contestprep
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Instantiate NotificationHelper to send the notification
        val notificationHelper = NotificationHelper(context)
        notificationHelper.sendMotivationalNotification()
    }
}
