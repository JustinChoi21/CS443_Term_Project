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
import edu.umb.cs443termproject.adapter.SelectCarItemAdapter
import edu.umb.cs443termproject.data.HistoryItems
import edu.umb.cs443termproject.data.SelectCarItems

class SelectCarFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : SelectCarFragment {
            return SelectCarFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "SelectCarFragment - onCreateView() called")
        return inflater.inflate(R.layout.fragment_select_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectCarList = arrayListOf(
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru", " Outaback 2022"),
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_select_car)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) // it makes good performance
        recyclerView.adapter = SelectCarItemAdapter(selectCarList)
    }
}