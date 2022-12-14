package edu.umb.cs443termproject.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import edu.umb.cs443termproject.MainActivity

class TireAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.createChannels("CS443Tire", "Tire reminder")
        notificationHelper.showNotification("CS443Tire", 1003, "Tire reminder", "Change Tire")

    }
}