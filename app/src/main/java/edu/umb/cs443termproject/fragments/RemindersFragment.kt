package edu.umb.cs443termproject.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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

    private lateinit var notificationHelper: NotificationHelper
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchRefuelReminder: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchEngineOilReminder: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchTireReminder: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchRegularServiceReminder: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(HomeFragment.TAG, "RemindersFragment - onCreateView() called")

        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize notificationHelper
        notificationHelper = NotificationHelper(context)

        switchRefuelReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_refuel_reminder)
        switchEngineOilReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_engine_oil_reminder)
        switchTireReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_tire_reminder)
        switchRegularServiceReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_regular_service_reminder)

        // set switch event listener
        switchRefuelReminder.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                Log.d(TAG, "Refuel reminder is on")
                val title = "CS443 Reminder"
                val message = "Your refuel reminder is on. This reminder will notify you to refuel on the target date."
                notificationHelper.showNotification(title, message)
            } else {
                Log.d(TAG, "Refuel reminder is off")
            }
        }
        switchEngineOilReminder.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                Log.d(TAG, "Engine oil reminder is on")
                val title = "CS443 Reminder"
                val message = "Your engine oil reminder is on. This reminder will notify you to change engine oil on the target date."
                notificationHelper.showNotification(title, message)
            } else {
                Log.d(TAG, "Engine oil reminder is off")
            }
        }
        switchTireReminder.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                Log.d(TAG, "Tire reminder is on")
                val title = "CS443 Reminder"
                val message = "Your tire reminder is on. This reminder will notify you to change tire on the target date."
                notificationHelper.showNotification(title, message)
            } else {
                Log.d(TAG, "Tire reminder is off")
            }
        }
        switchRegularServiceReminder.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                Log.d(TAG, "Regular service reminder is on")
                val title = "CS443 Reminder"
                val message = "Your regular service reminder is on. This reminder will notify you to do regular service on the target date."
                notificationHelper.showNotification(title, message)
            } else {
                Log.d(TAG, "Regular service reminder is off")
            }
        }

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
    } // onViewCreated() End
}