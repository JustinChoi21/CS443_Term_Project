package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
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
        list.add(BarEntry(1f, 10f))
        list.add(BarEntry(2f, 20f))
        list.add(BarEntry(3f, 30f))
        list.add(BarEntry(4f, 40f))
        list.add(BarEntry(5f, 50f))
        list.add(BarEntry(6f, 60f))
        list.add(BarEntry(7f, 70f))
        list.add(BarEntry(8f, 80f))
        list.add(BarEntry(9f, 90f))
        list.add(BarEntry(10f, 100f))
        list.add(BarEntry(11f, 110f))
        list.add(BarEntry(12f, 120f))

        val barDataSet = BarDataSet(list, "Fuel Consumption (Gallon)")
        val data = BarData(barDataSet)
        barChart?.data = data
        barChart?.description?.text = ""
        barChart?.setFitBars(true)
        barChart?.animateY(200)

        // barchart x axis
        barChart?.xAxis?.setDrawLabels(true)
        barChart?.xAxis?.setDrawAxisLine(true)
        barChart?.xAxis?.setDrawGridLines(true)
        barChart?.xAxis?.setDrawLimitLinesBehindData(true)

        // barchart y axis
        barChart?.axisLeft?.setDrawLabels(true)
        barChart?.axisLeft?.setDrawAxisLine(true)
        barChart?.axisLeft?.setDrawGridLines(true)
        barChart?.axisLeft?.setDrawLimitLinesBehindData(true)

    }
}