package edu.umb.cs443termproject.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import edu.umb.cs443termproject.MainActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intent = Intent(context, MainActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
}