package edu.umb.cs443termproject.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.adapter.HistoryItemAdapter
import edu.umb.cs443termproject.adapter.HomeItemAdapter
import edu.umb.cs443termproject.data.EventType
import edu.umb.cs443termproject.data.HistoryItems
import edu.umb.cs443termproject.data.SelectedCarItems
import edu.umb.cs443termproject.room.Car
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "HistoryFragment - onCreateView() called")
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val activity = view.context as AppCompatActivity
            val historyList: List<History> =
                RoomHelper.getDatabase(activity).getHistoryDao().getAllHistory()

            if (historyList.isNotEmpty()) {
                Log.d(
                    TAG, "onViewCreated: history : " + historyList.get(0).eventType + " / "
                            + historyList.get(0).eventDate + " / " + historyList.get(0).eventDescription
                )

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
                    val recyclerView: RecyclerView = view.findViewById(R.id.rv_history)
                    recyclerView.layoutManager = LinearLayoutManager(
                        this@HistoryFragment.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    recyclerView.setHasFixedSize(true) // it makes good performance
                    recyclerView.adapter = HistoryItemAdapter(historyArrayList)
                }

            } else {
                Log.d(TAG, "onViewCreated: history is empty")

                // show empty message & button to add dummy data
                showNoDataText(view)

            }
        }

    } // end of onViewCreated()


    private fun showNoDataText(view: View) {

        // add textview to show no data
        val textView = TextView(this.context)
        textView.text = "No History Data"
        textView.textSize = 20f
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.BLACK)
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        (view as ViewGroup).addView(textView)

        // add button to add dummy data
        val button = Button(this.context)
        button.text = "Add Dummy Data"
        button.textSize = 20f
        button.gravity = Gravity.CENTER
        button.setTextColor(Color.BLACK)
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        )
        (view as ViewGroup).addView(button)

        // when button is clicked, add dummy data
        button.setOnClickListener {
            lifecycleScope.launch {
                val activity = view.context as AppCompatActivity

                // create dummy data for testing
                val historyList = arrayListOf(
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "12/12/22", "Fuel Amount: 10Gal, Price: $30", 10, 30),
                    HistoryItems(R.drawable.list_icon_oil, EventType.EngineOil.value, "12/12/22", "At service center, price $50", 0, 0),
                    HistoryItems(R.drawable.list_icon_tire, EventType.Tire.value, "12/12/22", "At Service center, price $100", 0, 0),
                    HistoryItems(R.drawable.list_icon_service, EventType.RegularService.value, "12/12/22", "At Service center, price $120", 0, 0),

                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "12/01/22", "Fuel Amount: 5Gal, Price: $15", 5, 15),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "11/13/22", "Fuel Amount: 25Gal, Price: $75", 25, 75),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "10/15/22", "Fuel Amount: 15Gal, Price: $45", 15, 45),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "09/20/22", "Fuel Amount: 15Gal, Price: $45", 15, 45),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "08/21/22", "Fuel Amount: 20Gal, Price: $60", 20, 60),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "07/10/22", "Fuel Amount: 25Gal, Price: $75", 25, 75),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "06/11/22", "Fuel Amount: 23Gal, Price: $69", 23, 69),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "05/15/22", "Fuel Amount: 18Gal, Price: $54", 18, 54),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "04/15/22", "Fuel Amount: 14Gal, Price: $42", 14, 42),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "03/15/22", "Fuel Amount: 21Gal, Price: $63", 21, 63),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "02/11/22", "Fuel Amount: 19Gal, Price: $57", 19, 57),
                    HistoryItems(R.drawable.list_icon_fuel, EventType.Refuel.value, "01/23/22", "Fuel Amount: 12Gal, Price: $36", 12, 36),
                )

                // add dummy data to database
                for (history in historyList) {
                    RoomHelper.getDatabase(activity).getHistoryDao().addHistory(
                        History(
                            history.eventType,
                            history.eventDate,
                            history.eventDescription,
                            history.fuelAmount,
                            history.fuelPrice
                        )
                    )
                }

                // Refresh this fragment
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, HistoryFragment.newInstance()).commit()
            }
        }
    } // end of showNoDataText()
}