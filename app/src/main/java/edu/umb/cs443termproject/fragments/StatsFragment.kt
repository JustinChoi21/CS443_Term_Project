package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.EventType
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : StatsFragment {
            return StatsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(HomeFragment.TAG, "RemindersFragment - onCreateView() called")
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(HomeFragment.TAG, "RemindersFragment - onViewCreated() called")

        // hide floating action button
        val fab = (activity as MainActivity).findViewById<View>(R.id.floatingActionButton)
        fab.visibility = View.GONE

        // retrieve history data from database
        lifecycleScope.launch {
            val activity = view.context as AppCompatActivity
            val historyList: List<History> =
                RoomHelper.getDatabase(activity).getHistoryDao().getAllHistory()

            // set refuelList
            val refuelList = historyList.filter { it.eventType == EventType.Refuel.value }

            // set monthly refuel data
            var janRefuel = 0
            var febRefuel = 0
            var marRefuel = 0
            var aprRefuel = 0
            var mayRefuel = 0
            var junRefuel = 0
            var julRefuel = 0
            var augRefuel = 0
            var sepRefuel = 0
            var octRefuel = 0
            var novRefuel = 0
            var decRefuel = 0

            // set monthly refuel data
            for (history in refuelList) {
                when (history.eventDate.substring(0, 2)) {
                    "01" -> janRefuel += history.fuelAmount
                    "02" -> febRefuel += history.fuelAmount
                    "03" -> marRefuel += history.fuelAmount
                    "04" -> aprRefuel += history.fuelAmount
                    "05" -> mayRefuel += history.fuelAmount
                    "06" -> junRefuel += history.fuelAmount
                    "07" -> julRefuel += history.fuelAmount
                    "08" -> augRefuel += history.fuelAmount
                    "09" -> sepRefuel += history.fuelAmount
                    "10" -> octRefuel += history.fuelAmount
                    "11" -> novRefuel += history.fuelAmount
                    "12" -> decRefuel += history.fuelAmount
                }
            }

            withContext(Dispatchers.Main) {
                // bar chart
                val barChart = activity?.findViewById<BarChart>(R.id.barChart)
                val list: ArrayList<BarEntry> = ArrayList()
                list.add(BarEntry(0.5f, janRefuel.toFloat()))
                list.add(BarEntry(1.5f, febRefuel.toFloat()))
                list.add(BarEntry(2.5f, marRefuel.toFloat()))
                list.add(BarEntry(3.5f, aprRefuel.toFloat()))
                list.add(BarEntry(4.5f, mayRefuel.toFloat()))
                list.add(BarEntry(5.5f, junRefuel.toFloat()))
                list.add(BarEntry(6.5f, julRefuel.toFloat()))
                list.add(BarEntry(7.5f, augRefuel.toFloat()))
                list.add(BarEntry(8.5f, sepRefuel.toFloat()))
                list.add(BarEntry(9.5f, octRefuel.toFloat()))
                list.add(BarEntry(10.5f, novRefuel.toFloat()))
                list.add(BarEntry(11.5f, decRefuel.toFloat()))

                val barDataSet = BarDataSet(list, "Fuel Consumption (Gallon)")
                val data = BarData(barDataSet)
                barChart?.data = data
                barChart?.description?.text = ""
                barChart?.setFitBars(false)
                barChart?.animateY(200)

                // barchart x axis
                val xAxis: XAxis? = barChart?.xAxis
                xAxis?.setDrawLabels(true)
                xAxis?.setDrawAxisLine(true)
                xAxis?.setDrawGridLines(true)
                xAxis?.setDrawLimitLinesBehindData(true)

                xAxis?.position = XAxis.XAxisPosition.BOTTOM

                // set xAxis labels to months
                val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                xAxis?.valueFormatter = IndexAxisValueFormatter(months)
                xAxis?.labelCount = months.size
                xAxis?.setCenterAxisLabels(true)

                // barchart y axis
                val yAxis: YAxis? = barChart?.axisLeft
                yAxis?.setDrawLabels(true)
                yAxis?.setDrawAxisLine(true)
                yAxis?.setDrawGridLines(true)
                yAxis?.setDrawLimitLinesBehindData(true)
            } // withContext

        } // lifecycleScope.launch

    } // onViewCreated

} // StatsFragment