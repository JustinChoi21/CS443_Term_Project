package edu.umb.cs443termproject.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import edu.umb.cs443termproject.MainActivity

class NotificationHelper {

    companion object {

        val CHANNEL_ID = "CS443 channel id"

        fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, appName: String, description: String): NotificationManager {

            // don't need to check if sdk is greater than oreo because the min sdk version of this project is 26

            // Created a notification channel for this app.
            val channel = NotificationChannel(CHANNEL_ID, appName, importance).apply {
                this.description = description
                this.setShowBadge(showBadge)
            }

            // register the notification channel by passing it to the NotificationManager.createNotificationChannel() method
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            return notificationManager
        }
    }
}