package edu.umb.cs443termproject.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import edu.umb.cs443termproject.MainActivity

class EngineOilAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.createChannels("CS443EngineOil", "Engine Oil reminder")
        notificationHelper.showNotification("CS443EngineOil", 1002, "Engine Oil reminder", "Change Engine Oil")

    }
}