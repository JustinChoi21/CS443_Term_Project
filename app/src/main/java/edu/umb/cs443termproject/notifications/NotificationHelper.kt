package edu.umb.cs443termproject.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat
import edu.umb.cs443termproject.R

class NotificationHelper(base: Context?) : ContextWrapper(base) {

    // channel id and name
    private val channelID = "CS443ChannelId"
    private val channelName = "CS443ChannelName"

    init {

        // Notification Channel is only exist in API 26+ (Android Oreo or higher version)))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }

    }

    private fun createChannels() {
        val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        getManager().createNotificationChannel(channel)
    }

    // Create NotificationManager
    fun getManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    // setting Notification
    fun getChannelNotification(title: String, body: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_baseline_directions_car_24)
            .setAutoCancel(true)
    }

}