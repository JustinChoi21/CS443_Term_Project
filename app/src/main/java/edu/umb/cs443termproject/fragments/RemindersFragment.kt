package edu.umb.cs443termproject.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.EventType
import edu.umb.cs443termproject.notifications.AlarmReceiver
import edu.umb.cs443termproject.notifications.NotificationHelper
import edu.umb.cs443termproject.room.Car
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.Reminder
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class RemindersFragment : Fragment() {

    private lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

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
        notificationHelper.createChannels("CS443ReminderSettings", "CS443ReminderSettings") // this channel is for the notification of each reminders (refuel, engine oil, tire, regular service) is on
        notificationHelper.createChannels("CS443RefuelAlarm", "CS443RefuelAlarm")
        notificationHelper.createChannels("CS443EngineOilAlarm", "CS443EngineOilAlarm")
        notificationHelper.createChannels("CS443TireAlarm", "CS443TireAlarm")
        notificationHelper.createChannels("CS443RegularServiceAlarm", "CS443RegularServiceAlarm")


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

        // todo: retrieve the alarm time from the database
        lifecycleScope.launch {

        }

        // when select set alarm time button
        val setAlarmTimeButton = (activity as MainActivity).findViewById<TextView>(R.id.btn_set_alarm_time)
        val alarmTimeTextView = (activity as MainActivity).findViewById<TextView>(R.id.tv_set_alarm_time)
        setAlarmTimeButton.setOnClickListener {
            // show time picker dialog
            picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build()

            picker.show(activity.supportFragmentManager, "CS443")

            // when the time is selected
            picker.addOnPositiveButtonClickListener {
                val hour = picker.hour
                val minute = picker.minute
                val time = "$hour:$minute"

                var minuteString = minute.toString()
                if(minute < 10) {
                    minuteString = "0$minute"
                }

                if(hour > 12) {
                    alarmTimeTextView.text = "${hour - 12}:$minuteString PM"
                } else {
                    alarmTimeTextView.text = "$hour:$minuteString AM"
                }

                // update the alarm time in the database

                // update the time of the previous alarm
                alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
//                alarmManager.cancel(pendingIntent)

                // set the new alarm
                val year = Calendar.getInstance().get(Calendar.YEAR)
                val month = Calendar.getInstance().get(Calendar.MONTH)
                val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

                val calendar = Calendar.getInstance()
                calendar.set(year, month, day, hour, minute, 0)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

                // show message
                Toast.makeText(context, "Alarm time is set", Toast.LENGTH_SHORT).show()

                Log.d(TAG, "onViewCreated: alarm time is set")

            } // end of addOnPositiveButtonClickListener

        } // end of setAlarmTimeButton.setOnClickListener



        // set switch event listener
        switchRefuelReminder.setOnClickListener() {
            val isChecked = switchRefuelReminder.isChecked
            if (isChecked) {
                // show notification of refuel reminder is on
                val title = "Refuel Reminder is On"
                val message = "This reminder will notify you to refuel on the target date."
                notificationHelper.showNotification("CS443ReminderSettings", 1001, title, message)
                switchRefuelReminder.isChecked = true

                // set alarm for refuel reminder

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
                val title = "Engine Oil Change Reminder is On"
                val message = "This reminder will notify you to change engine oil on the target date."
                notificationHelper.showNotification("CS443ReminderSettings", 1002, title, message)
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
                val title = "Tire Change Reminder is On"
                val message = "This reminder will notify you to change tire on the target date."
                notificationHelper.showNotification("CS443ReminderSettings", 1003 ,title, message)
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
                val title = "Regular Service Reminder is On"
                val message = "This reminder will notify you to do regular service on the target date."
                notificationHelper.showNotification("CS443ReminderSettings", 1004, title, message)
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


        // retrieve history & car data from database and set reminder date
        lifecycleScope.launch {
            val activity = view.context as AppCompatActivity
            val historyList: List<History> =
                RoomHelper.getDatabase(activity).getHistoryDao().getAllHistory()

            val refuelList = historyList.filter { it.eventType == EventType.Refuel.value }
            val engineOilList = historyList.filter { it.eventType == EventType.EngineOil.value }
            val tireList = historyList.filter { it.eventType == EventType.Tire.value }
            val regularServiceList = historyList.filter { it.eventType == EventType.RegularService.value }

            refuelList.sortedByDescending { it.eventDate }
            engineOilList.sortedByDescending { it.eventDate }
            tireList.sortedByDescending { it.eventDate }
            regularServiceList.sortedByDescending { it.eventDate }

            // last date of each event type
            val datePattern = "MM/dd/yyyy"
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)

            val lastRefuelDate = if (refuelList.isNotEmpty()) {
                LocalDate.parse(refuelList[0].eventDate, dateFormat)
            } else {
                LocalDate.now()
            }

            val lastEngineOilDate = if (engineOilList.isNotEmpty()) {
                LocalDate.parse(engineOilList[0].eventDate, dateFormat)
            } else {
                LocalDate.now()
            }

            val lastTireDate = if (tireList.isNotEmpty()) {
                LocalDate.parse(tireList[0].eventDate, dateFormat)
            } else {
                LocalDate.now()
            }

            val lastRegularServiceDate = if (regularServiceList.isNotEmpty()) {
                LocalDate.parse(regularServiceList[0].eventDate, dateFormat)
            } else {
                LocalDate.now()
            }

            // retrieve car data from database
            val carList: List<Car> =
                RoomHelper.getDatabase(activity).getCarDao().getAllCar()

            val refuelFuelTankCapacity = carList[0].fuelTank
            val engineOilInterval = carList[0].engineOil
            val tireInterval = carList[0].tire
            val regularServiceInterval = carList[0].regularService

            // calculate the reminder date of each event type
            val refuelReminderDate = lastRefuelDate.plusMonths(1L).format(dateFormat)
            val engineOilReminderDate = lastEngineOilDate.plusMonths(engineOilInterval.toLong()).format(dateFormat)
            val tireReminderDate = lastTireDate.plusMonths(tireInterval.toLong()).format(dateFormat)
            val regularServiceReminderDate = lastRegularServiceDate.plusMonths(regularServiceInterval.toLong()).format(dateFormat)

            // set the last event date to the reminder date
            withContext(Dispatchers.Main) {
                activity.findViewById<TextView>(R.id.tv_refuel_reminder_description)
                    .text = "Refuel your car on $refuelReminderDate"
                activity.findViewById<TextView>(R.id.tv_engine_oil_reminder_description)
                    .text = "Change engine oil on $engineOilReminderDate"
                activity.findViewById<TextView>(R.id.tv_tire_reminder_description)
                    .text = "Change tire on $tireReminderDate"
                activity.findViewById<TextView>(R.id.tv_regular_service_reminder_description)
                    .text = "Do regular service on $regularServiceReminderDate"
            }

        } // end of lifecycleScope.launch

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