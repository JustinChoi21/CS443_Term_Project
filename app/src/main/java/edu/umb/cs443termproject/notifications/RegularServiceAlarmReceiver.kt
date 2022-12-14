package edu.umb.cs443termproject.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import edu.umb.cs443termproject.MainActivity

class RegularServiceAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.createChannels("CS443RegularService", "RegularService reminder")
        notificationHelper.showNotification("CS443RegularService", 1004, "RegularService reminder", "Do Regular Service")

    }
}