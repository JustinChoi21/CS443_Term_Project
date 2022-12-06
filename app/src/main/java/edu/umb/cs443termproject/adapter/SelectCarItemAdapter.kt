package edu.umb.cs443termproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.CarItems
import edu.umb.cs443termproject.fragments.HomeFragment
import edu.umb.cs443termproject.room.Car
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class SelectCarItemAdapter(val carList: ArrayList<CarItems>) : RecyclerView.Adapter<SelectCarItemAdapter.CustomViewHolder>() {

    companion object {
        const val TAG: String = "CS443"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCarItemAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_select_car, parent, false)
        return CustomViewHolder(view)

//        return CustomViewHolder(view).apply {
//            itemView.setOnClickListener{
//                val curPos : Int = adapterPosition
//                val selectCarItem : CarItems = selectCarList.get(curPos)
//
//                Toast.makeText(parent.context,
//                    "ManufacturerName: " + selectCarItem.ManufacturerName + " / model : " + selectCarItem.model,
//                    Toast.LENGTH_LONG).show()
//            }
//        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    // called when we scroll or use the list
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.icon.setImageResource(carList.get(position).icon)
        holder.manufacturer_model.text =
            carList.get(position).ManufacturerName + " " + carList.get(position).model

        // store selected car info & move to homeFragment
        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d(TAG, "onClick: holder.itemView.setOnClickListener")

                // move to homeFragment
                val activity = v!!.context as AppCompatActivity

                // store selected car info
                var icon: Int = carList.get(position).icon
                val manufacturer: String = carList.get(position).ManufacturerName
                val model: String = carList.get(position).model

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val selectedDate = LocalDateTime.now().format(formatter)

                activity.lifecycleScope.launch{
                    val car = Car(icon, manufacturer, model, selectedDate)
                    RoomHelper.getDatabase(activity).getCarDao().addCar(car)
                }

                val homeFragment = HomeFragment.newInstance()
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, homeFragment)
                    .addToBackStack(null).commit()
            }
        })

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val icon = itemView.findViewById<ImageView>(R.id.iv_list_icon_select_car)
        val manufacturer_model = itemView.findViewById<TextView>(R.id.tv_manufacturer_model)
    }

}