package edu.umb.cs443termproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.CarItems
import edu.umb.cs443termproject.fragments.HomeFragment
import edu.umb.cs443termproject.room.Car
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.coroutines.launch

class SelectCarItemAdapter(val selectCarList: ArrayList<CarItems>) : RecyclerView.Adapter<SelectCarItemAdapter.CustomViewHolder>() {

    companion object {
        const val TAG: String = "CS443"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCarItemAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_select_car, parent, false)
        // return CustomViewHolder(view)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{
                val curPos : Int = adapterPosition
                val selectCarItem : CarItems = selectCarList.get(curPos)

                Toast.makeText(parent.context,
                    "ManufacturerName: " + selectCarItem.ManufacturerName + " / model : " + selectCarItem.model,
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return selectCarList.size
    }

    // called when we scroll or use the list
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.icon.setImageResource(selectCarList.get(position).icon)
        holder.manufacturer_model.text =
            selectCarList.get(position).ManufacturerName + " " + selectCarList.get(position).model

        // store selected car info & move to homeFragment
        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d(TAG, "onClick: holder.itemView.setOnClickListener")

                // move to homeFragment
                val activity = v!!.context as AppCompatActivity

                // store selected car info
                var icon: Int = selectCarList.get(position).icon
                val manufacturer: String = selectCarList.get(position).ManufacturerName
                val model: String = selectCarList.get(position).model

                activity.lifecycleScope.launch{
                    val car = Car(icon, manufacturer, model)
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