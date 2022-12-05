package edu.umb.cs443termproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.CarItems
import edu.umb.cs443termproject.fragments.SelectCarFragment

class HomeItemAdapter(val carList: ArrayList<CarItems>) : RecyclerView.Adapter<HomeItemAdapter.CustomViewHolder>() {

    companion object {
        const val TAG: String = "CS443"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false)
        // return CustomViewHolder(view)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{
                val curPos : Int = adapterPosition
                val selectCarItem : CarItems = carList.get(curPos)

//                Toast.makeText(parent.context,
//                    "ManufacturerName: " + selectCarItem.ManufacturerName + " / model : " + selectCarItem.model,
//                    Toast.LENGTH_LONG).show()
            }
        }
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
        holder.btnChangeCar.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d(TAG, "onClick: holder.btnChangeCar.setOnClickListener")

                val activity = v!!.context as AppCompatActivity
                activity.title = "Select Your Car"

                val selectCarFragment = SelectCarFragment.newInstance()
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, selectCarFragment).commit()
            }
        })

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val icon = itemView.findViewById<ImageView>(R.id.iv_list_car_image)
        val manufacturer_model = itemView.findViewById<TextView>(R.id.tv_my_car_manufacturer_model)
        var btnChangeCar = itemView.findViewById<Button>(R.id.btn_change_my_car)
    }

}