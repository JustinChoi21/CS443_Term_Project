package edu.umb.cs443termproject.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import edu.umb.cs443termproject.MainActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.createChannels("edu.umb.cs443termproject", "edu.umb.cs443termproject")
        notificationHelper.showNotification("edu.umb.cs443termproject", 1, "Alarm", "Alarm is ringing")

    }
}