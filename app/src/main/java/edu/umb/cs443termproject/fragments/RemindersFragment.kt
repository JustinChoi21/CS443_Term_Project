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
import edu.umb.cs443termproject.data.EventType
import edu.umb.cs443termproject.data.HistoryItems
import edu.umb.cs443termproject.notifications.NotificationHelper
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.Reminder
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


        // retrieve the switch conditions from the database & set the switch status
        switchRefuelReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_refuel_reminder)
        switchEngineOilReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_engine_oil_reminder)
        switchTireReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_tire_reminder)
        switchRegularServiceReminder = (activity as MainActivity).findViewById<Switch>(R.id.switch_regular_service_reminder)

        val activity = view.context as AppCompatActivity
        lifecycleScope.launch {
            val reminderList : List<Reminder> = RoomHelper.getDatabase(activity).getReminderDao().getAllReminders()
            if(reminderList.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    switchRefuelReminder.isChecked = reminderList[0].refuel == 1
                    switchEngineOilReminder.isChecked = reminderList[0].engineOil == 1
                    switchTireReminder.isChecked = reminderList[0].tire == 1
                    switchRegularServiceReminder.isChecked = reminderList[0].regularService == 1
                }
            } else {
                // add reminder data to the database
                val reminder = Reminder(0, 0, 0, 0)
                RoomHelper.getDatabase(activity).getReminderDao().addReminder(reminder)
            }
        }


        // set switch event listener
        val title = "CS443 Reminder"

        switchRefuelReminder.setOnClickListener() {
            val isChecked = switchRefuelReminder.isChecked
            if (isChecked) {
                Log.d(TAG, "Refuel reminder is on")
                val message = "Your refuel reminder is on. This reminder will notify you to refuel on the target date."
                notificationHelper.showNotification(title, message)
                switchRefuelReminder.isChecked = true
            } else {
                Log.d(TAG, "Refuel reminder is off")
                switchRefuelReminder.isChecked = false
            }
            updateReminderStatus()
        }
        switchEngineOilReminder.setOnClickListener() {
            val isChecked = switchEngineOilReminder.isChecked
            if (isChecked) {
                Log.d(TAG, "Engine oil reminder is on")
                val message = "Your engine oil reminder is on. This reminder will notify you to change engine oil on the target date."
                notificationHelper.showNotification(title, message)
                switchEngineOilReminder.isChecked = true
            } else {
                Log.d(TAG, "Engine oil reminder is off")
                switchEngineOilReminder.isChecked = false
            }
            updateReminderStatus()
        }
        switchTireReminder.setOnClickListener() {
            val isChecked = switchTireReminder.isChecked
            if (isChecked) {
                Log.d(TAG, "Tire reminder is on")
                val message = "Your tire reminder is on. This reminder will notify you to change tire on the target date."
                notificationHelper.showNotification(title, message)
                switchTireReminder.isChecked = true
            } else {
                Log.d(TAG, "Tire reminder is off")
                switchTireReminder.isChecked = false
            }
            updateReminderStatus()
        }
        switchRegularServiceReminder.setOnClickListener() {
            val isChecked = switchRegularServiceReminder.isChecked
            if (isChecked) {
                Log.d(TAG, "Regular service reminder is on")
                val message = "Your regular service reminder is on. This reminder will notify you to do regular service on the target date."
                notificationHelper.showNotification(title, message)
                switchRegularServiceReminder.isChecked = true
            } else {
                Log.d(TAG, "Regular service reminder is off")
                switchRegularServiceReminder.isChecked = false
            }
            updateReminderStatus()
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
                        EventType.Refuel.value -> R.drawable.list_icon_fuel
                        EventType.EngineOil.value -> R.drawable.list_icon_oil
                        EventType.Tire.value -> R.drawable.list_icon_tire
                        EventType.RegularService.value -> R.drawable.list_icon_service
                        else -> R.drawable.list_icon_fuel
                    }

                    historyArrayList.add(
                        HistoryItems(
                            history.id,
                            icon,
                            history.eventType,
                            history.eventDate,
                            history.eventDescription,
                            history.fuelAmount,
                            history.fuelPrice
                        )
                    )
                }

                withContext(Dispatchers.Main) {
                    // todo: set reminder date & alarm

                }

            } else {

                // todo: no reminder data
                Log.d(HomeFragment.TAG, "onViewCreated: no reminder data")

            }
        }
    } // onViewCreated() End


    private fun updateReminderStatus() {
        val refuel = if(switchRefuelReminder.isChecked) 1 else 0
        val engineOil = if(switchEngineOilReminder.isChecked) 1 else 0
        val tire = if(switchTireReminder.isChecked) 1 else 0
        val regularService = if(switchRegularServiceReminder.isChecked) 1 else 0

        val reminder = Reminder(refuel, engineOil, tire, regularService)
        lifecycleScope.launch {
            RoomHelper.getDatabase(context as AppCompatActivity).getReminderDao().updateReminder(reminder)
        }
    }
}