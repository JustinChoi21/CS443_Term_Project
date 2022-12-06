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
import edu.umb.cs443termproject.adapter.HomeItemAdapter
import edu.umb.cs443termproject.data.SelectedCarItems
import edu.umb.cs443termproject.room.Car
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "HomeFragment - onCreateView() called")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // retrieve car info
        lifecycleScope.launch {
            val activity = view.context as AppCompatActivity
            val carList: List<Car> = RoomHelper.getDatabase(activity).getCarDao().getAllCar()

            if (carList.isNotEmpty()) {
                Log.d(TAG, "onViewCreated: car model : " + carList.get(0).model)

                // retrieve first car
                val carArrayList: ArrayList<SelectedCarItems> = arrayListOf()
                carArrayList.add(SelectedCarItems(
                    carList[0].icon,
                    carList[0].manufacturer,
                    carList[0].model,
                    carList[0].selectedDate)
                )

                withContext(Dispatchers.Main) {
                    val recyclerView: RecyclerView = view.findViewById(R.id.rv_home)
                    recyclerView.layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.VERTICAL, false)
                    recyclerView.setHasFixedSize(true) // it makes good performance
                    recyclerView.adapter = HomeItemAdapter(carArrayList)
                }

            } else {

                // move to select car
                Log.d(TAG, "onViewCreated: need to select car")
                activity.title = "Select Your Car"
                val selectCarFragment = SelectCarFragment.newInstance()
                activity.supportFragmentManager.beginTransaction().add(R.id.fragments_frame, selectCarFragment).commit()
            }
        }
    }
}