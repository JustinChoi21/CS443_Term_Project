package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import kotlinx.android.synthetic.main.fragment_stats.*

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

        // bar chart
        val barChart = activity?.findViewById<BarChart>(R.id.barChart)
        val list: ArrayList<BarEntry> = ArrayList()
        list.add(BarEntry(0.5f, 10f))
        list.add(BarEntry(1.5f, 20f))
        list.add(BarEntry(2.5f, 30f))
        list.add(BarEntry(3.5f, 40f))
        list.add(BarEntry(4.5f, 50f))
        list.add(BarEntry(5.5f, 60f))
        list.add(BarEntry(6.5f, 70f))
        list.add(BarEntry(7.5f, 80f))
        list.add(BarEntry(8.5f, 90f))
        list.add(BarEntry(9.5f, 100f))
        list.add(BarEntry(10.5f, 110f))
        list.add(BarEntry(11.5f, 120f))

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


    }
}