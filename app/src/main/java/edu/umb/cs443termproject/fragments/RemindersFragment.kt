package edu.umb.cs443termproject.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
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
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.EventType
import edu.umb.cs443termproject.notifications.*
import edu.umb.cs443termproject.room.Car
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.Reminder
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class RemindersFragment : Fragment() {

    private lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private val SHARED_PREFS = "sharedPrefs"

    private lateinit var refuelReminderDate: LocalDate
    private lateinit var engineOilReminderDate: LocalDate
    private lateinit var tireReminderDate: LocalDate
    private lateinit var regularServiceReminderDate: LocalDate

    private lateinit var pendingIntentRefuel: PendingIntent
    private lateinit var pendingIntentEngineOil: PendingIntent
    private lateinit var pendingIntentTire: PendingIntent
    private lateinit var pendingIntentRegularService: PendingIntent

    private var alarmHour: Int = 10
    private var alarmMinute: Int = 0

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

        // shared preferences
        sharedPreferences = (activity as MainActivity).getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()

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
            refuelReminderDate = lastRefuelDate.plusMonths(1L)
            val refuelReminderDateString = refuelReminderDate.format(dateFormat)

            engineOilReminderDate = lastEngineOilDate.plusMonths(engineOilInterval.toLong())
            val engineOilReminderDateString = engineOilReminderDate.format(dateFormat)

            tireReminderDate = lastTireDate.plusMonths(tireInterval.toLong())
            val tireReminderDateString = tireReminderDate.format(dateFormat)

            regularServiceReminderDate = lastRegularServiceDate.plusMonths(regularServiceInterval.toLong())
            val regularServiceReminderDateString = regularServiceReminderDate.format(dateFormat)


            // set the last event date to the reminder date
            withContext(Dispatchers.Main) {
                activity.findViewById<TextView>(R.id.tv_refuel_reminder_description)
                    .text = "Refuel your car on $refuelReminderDateString"
                activity.findViewById<TextView>(R.id.tv_engine_oil_reminder_description)
                    .text = "Change engine oil on $engineOilReminderDateString"
                activity.findViewById<TextView>(R.id.tv_tire_reminder_description)
                    .text = "Change tire on $tireReminderDateString"
                activity.findViewById<TextView>(R.id.tv_regular_service_reminder_description)
                    .text = "Do regular service on $regularServiceReminderDateString"
            }
        } // end of lifecycleScope.launch


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

        // retrieve the alarm time from shared preferences & set the text view
        val alarmTime = sharedPreferences.getString("alarmTime", "10:00")
        setAlarmTimeText(
            alarmTime?.split(":")?.get(0)?.toInt()!!,
            alarmTime?.split(":")?.get(1)?.toInt()!!
        )


        // initialize for the alarm
        initNotificationHelper()

        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intentRefuel = Intent(context, RefuelAlarmReceiver::class.java)
        val intentEngineOil = Intent(context, EngineOilAlarmReceiver::class.java)
        val intentTire = Intent(context, TireAlarmReceiver::class.java)
        val intentRegularService = Intent(context, RegularServiceAlarmReceiver::class.java)

        val flag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
        pendingIntentRefuel = PendingIntent.getBroadcast(context, 1001, intentRefuel, flag)
        pendingIntentEngineOil = PendingIntent.getBroadcast(context, 1002, intentEngineOil, flag)
        pendingIntentTire = PendingIntent.getBroadcast(context, 1003, intentTire, flag)
        pendingIntentRegularService = PendingIntent.getBroadcast(context, 1004, intentRegularService, flag)

        // click the set alarm time button
        val setAlarmTimeButton = (activity as MainActivity).findViewById<TextView>(R.id.btn_set_alarm_time)
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

                alarmHour = picker.hour
                alarmMinute = picker.minute

                // edit the alarm time in shared preferences
                sharedPreferencesEditor.putString("alarmTime", "${alarmHour}:${alarmMinute}")
                sharedPreferencesEditor.apply()

                // cancel all previous alarms
                alarmManager.cancel(pendingIntentRefuel)
                alarmManager.cancel(pendingIntentEngineOil)
                alarmManager.cancel(pendingIntentTire)
                alarmManager.cancel(pendingIntentRegularService)

                // set the new alarm if the switch is on
                if(switchRefuelReminder.isChecked) { setAlarm(refuelReminderDate, alarmHour, alarmMinute, pendingIntentRefuel) }
                if(switchEngineOilReminder.isChecked) { setAlarm(engineOilReminderDate, alarmHour, alarmMinute, pendingIntentEngineOil) }
                if(switchTireReminder.isChecked) { setAlarm(tireReminderDate, alarmHour, alarmMinute, pendingIntentTire) }
                if(switchRegularServiceReminder.isChecked) { setAlarm(regularServiceReminderDate, alarmHour, alarmMinute, pendingIntentRegularService) }

                // set the selected time to the text view
                setAlarmTimeText(alarmHour, alarmMinute)

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
                setAlarm(refuelReminderDate, alarmHour, alarmMinute, pendingIntentRefuel)

            } else {
                Log.d(TAG, "Refuel reminder is off")
                switchRefuelReminder.isChecked = false

                // cancel alarm for refuel reminder
                alarmManager.cancel(pendingIntentRefuel)
            }
            updateReminderStatusInRoomDB()
        }

        switchEngineOilReminder.setOnClickListener() {
            val isChecked = switchEngineOilReminder.isChecked
            if (isChecked) {
                Log.d(TAG, "Engine oil reminder is on")
                val title = "Engine Oil Change Reminder is On"
                val message = "This reminder will notify you to change engine oil on the target date."
                notificationHelper.showNotification("CS443ReminderSettings", 1002, title, message)
                switchEngineOilReminder.isChecked = true

                // set alarm for engine oil reminder
                setAlarm(engineOilReminderDate, alarmHour, alarmMinute, pendingIntentEngineOil)

            } else {
                Log.d(TAG, "Engine oil reminder is off")
                switchEngineOilReminder.isChecked = false

                // cancel alarm for engine oil reminder
                alarmManager.cancel(pendingIntentEngineOil)
            }
            updateReminderStatusInRoomDB()
        }

        switchTireReminder.setOnClickListener() {
            val isChecked = switchTireReminder.isChecked
            if (isChecked) {
                Log.d(TAG, "Tire reminder is on")
                val title = "Tire Change Reminder is On"
                val message = "This reminder will notify you to change tire on the target date."
                notificationHelper.showNotification("CS443ReminderSettings", 1003 ,title, message)
                switchTireReminder.isChecked = true

                // set alarm for tire reminder
                setAlarm(tireReminderDate, alarmHour, alarmMinute, pendingIntentTire)

            } else {
                Log.d(TAG, "Tire reminder is off")
                switchTireReminder.isChecked = false

                // cancel alarm for tire reminder
                alarmManager.cancel(pendingIntentTire)

            }
            updateReminderStatusInRoomDB()
        }

        switchRegularServiceReminder.setOnClickListener() {
            val isChecked = switchRegularServiceReminder.isChecked
            if (isChecked) {
                Log.d(TAG, "Regular service reminder is on")
                val title = "Regular Service Reminder is On"
                val message = "This reminder will notify you to do regular service on the target date."
                notificationHelper.showNotification("CS443ReminderSettings", 1004, title, message)
                switchRegularServiceReminder.isChecked = true

                // set alarm for regular service reminder
                setAlarm(regularServiceReminderDate, alarmHour, alarmMinute, pendingIntentRegularService)

            } else {
                Log.d(TAG, "Regular service reminder is off")
                switchRegularServiceReminder.isChecked = false

                // cancel alarm for regular service reminder
                alarmManager.cancel(pendingIntentRegularService)
            }
            updateReminderStatusInRoomDB()
        }

        // hide floating action button
        val fab = (activity as MainActivity).findViewById<View>(R.id.floatingActionButton)
        fab.visibility = View.GONE


    } // onViewCreated() End


    private fun initNotificationHelper() {
        notificationHelper = NotificationHelper(context)
        // this channels are for the notification of each reminders (refuel, engine oil, tire, regular service) is on
        notificationHelper.createChannels("CS443ReminderSettings", "CS443ReminderSettings")
        notificationHelper.createChannels("CS443RefuelAlarm", "CS443RefuelAlarm")
        notificationHelper.createChannels("CS443EngineOilAlarm", "CS443EngineOilAlarm")
        notificationHelper.createChannels("CS443TireAlarm", "CS443TireAlarm")
        notificationHelper.createChannels("CS443RegularServiceAlarm", "CS443RegularServiceAlarm")
    }

    private fun updateReminderStatusInRoomDB() {
        val refuel = if(switchRefuelReminder.isChecked) 1 else 0
        val engineOil = if(switchEngineOilReminder.isChecked) 1 else 0
        val tire = if(switchTireReminder.isChecked) 1 else 0
        val regularService = if(switchRegularServiceReminder.isChecked) 1 else 0

        val reminder = Reminder(refuel, engineOil, tire, regularService)
        lifecycleScope.launch {
            RoomHelper.getDatabase(context as AppCompatActivity).getReminderDao().updateReminder(reminder)
        }
    }

    private fun setAlarmTimeText(hour24: Int, minute60: Int) {
        val alarmTimeTextView = (activity as MainActivity).findViewById<TextView>(R.id.tv_set_alarm_time)

        var minuteString = minute60.toString()

        if(minute60 < 10) {
            minuteString = "0$minute60"
        }

        if(hour24 > 12) {
            alarmTimeTextView.text = "${hour24 - 12}:$minuteString PM"
        } else {
            alarmTimeTextView.text = "$hour24:$minuteString AM"
        }
    }

    private fun setAlarm(remdinerDate: LocalDate, hour: Int, minute: Int, pendingIntent: PendingIntent) {
        val calendar = Calendar.getInstance()

        // real date
        val year = remdinerDate.year
        val month = remdinerDate.monthValue - 1
        val day = remdinerDate.dayOfMonth

        // test date
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)

        Log.d(TAG, "setAlarm: year: $year, month: $month, day: $day, hour: $hour, minute: $minute")

        calendar.set(year, month, day, hour, minute, 0)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    } // setAlarm() End

} // end of class