package com.mathy.contestprep

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val helper = NotificationHelper(context)
        helper.sendMotivationalNotification()

        // Schedule the next daily notification
        NotificationScheduler(context).scheduleDailyNotification()
    }
}