package edu.umb.cs443termproject.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import edu.umb.cs443termproject.MainActivity

class RefuelAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.createChannels("CS443Refuel", "Refuel reminder")
        notificationHelper.showNotification("CS443Refuel", 1001, "Refuel reminder", "Refuel your car")

    }
}