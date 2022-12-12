package edu.umb.cs443termproject.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import edu.umb.cs443termproject.LoginActivity
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.HistoryItems
import edu.umb.cs443termproject.notifications.NotificationHelper
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RemindersFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : RemindersFragment {
            return RemindersFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(HomeFragment.TAG, "RemindersFragment - onCreateView() called")

        val CHANNEL_ID = "CS443 channel id"
        val APP_NAME = R.string.app_name.toString()

        // Safety checked the OS version for API 26 and greater.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Created a notification channel for this app.
            val channel = NotificationChannel(CHANNEL_ID, APP_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                this.description = description
                this.setShowBadge(true)
            }

            // register the notification channel by passing it to the NotificationManager.createNotificationChannel() method
            val notificationManager: NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            // set pending intent
            val intent = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            // refer: https://stackoverflow.com/questions/67045607/how-to-resolve-missing-pendingintent-mutability-flag-lint-warning-in-android-a
            var pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            } else {
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            // create a notification
            var notificationBuilder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder")
                .setContentText("Your car is due for an oil change.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Your car is due for an oil change."))
                .setAutoCancel(true) // Set the notification to auto cancel when tapped.
                .setContentIntent(pendingIntent) // set pending intent

            // send the notification
            notificationManager.notify(1, notificationBuilder.build())
        }

        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hide floating action button
        val fab = (activity as MainActivity).findViewById<View>(R.id.floatingActionButton)
        fab.visibility = View.GONE

        // retrieve data from database
        lifecycleScope.launch {
            val activity = view.context as AppCompatActivity
            val historyList: List<History> =
                RoomHelper.getDatabase(activity).getHistoryDao().getAllHistory()

            if (historyList.isNotEmpty()) {

                // retrieve history & set historyArrayList
                val historyArrayList: ArrayList<HistoryItems> = arrayListOf()
                for (history in historyList) {

                    // eventType icon
                    var icon: Int = when (history.eventType) {
                        "Refuel" -> R.drawable.list_icon_fuel
                        "Change Engine Oil" -> R.drawable.list_icon_oil
                        "Change Tire" -> R.drawable.list_icon_tire
                        "Regular Service" -> R.drawable.list_icon_service
                        else -> R.drawable.list_icon_fuel
                    }

                    historyArrayList.add(
                        HistoryItems(
                            icon,
                            history.eventType,
                            history.eventDate,
                            history.eventDescription
                        )
                    )
                }

                withContext(Dispatchers.Main) {

                }

            } else {

                // todo: no reminder data
                Log.d(HomeFragment.TAG, "onViewCreated: no reminder data")

            }
        }
    }
}