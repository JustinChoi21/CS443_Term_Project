package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.adapter.HistoryItemAdapter
import edu.umb.cs443termproject.data.HistoryItems

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

        val historyList = arrayListOf(
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_oil, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_service, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_tire, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
            HistoryItems(R.drawable.list_icon_fuel, "Refuel", "Date: 10/25/2022, Fuel: 30L, Type: Regular"),
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_history)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) // it makes good performance
        recyclerView.adapter = HistoryItemAdapter(historyList)
    }
}