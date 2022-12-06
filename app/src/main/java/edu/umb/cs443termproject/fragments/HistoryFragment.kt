package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.adapter.HistoryItemAdapter
import edu.umb.cs443termproject.adapter.HomeItemAdapter
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
                    var icon: Int = R.drawable.list_icon_fuel
                    when (history.eventType) {
                        "Refuel" -> R.drawable.list_icon_fuel
                        "Engine Oil" -> R.drawable.list_icon_oil
                        "Tire" -> R.drawable.list_icon_tire
                        "Service" -> R.drawable.list_icon_service
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

                // todo: no history data
                Log.d(HomeFragment.TAG, "onViewCreated: no history data")

            }
        }

//        val historyList = arrayListOf(
//            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_oil, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_service, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_tire, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_oil, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_oil, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_service, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_tire, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_oil, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "10/25/22", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
//        )

//        val recyclerView: RecyclerView = view.findViewById(R.id.rv_history)
//        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
//        recyclerView.setHasFixedSize(true) // it makes good performance
//        recyclerView.adapter = HistoryItemAdapter(historyList)
    }
}