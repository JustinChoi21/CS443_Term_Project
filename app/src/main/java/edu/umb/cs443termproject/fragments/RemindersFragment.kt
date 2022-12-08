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
import edu.umb.cs443termproject.data.HistoryItems
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
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    val recyclerView: RecyclerView = view.findViewById(R.id.rv_reminders)
                    recyclerView.layoutManager = LinearLayoutManager(
                        this@RemindersFragment.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    recyclerView.setHasFixedSize(true) // it makes good performance
                    recyclerView.adapter = HistoryItemAdapter(historyArrayList)
                }

            } else {

                // todo: no reminder data
                Log.d(HomeFragment.TAG, "onViewCreated: no reminder data")

            }
        }
    }
}