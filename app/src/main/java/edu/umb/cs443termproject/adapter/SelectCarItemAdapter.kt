package edu.umb.cs443termproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.SelectCarItems

class SelectCarItemAdapter(val selectCarList: ArrayList<SelectCarItems>) : RecyclerView.Adapter<SelectCarItemAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCarItemAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_select_car, parent, false)
        // return CustomViewHolder(view)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{
                val curPos : Int = adapterPosition
                val selectCarItem : SelectCarItems = selectCarList.get(curPos)
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
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val icon = itemView.findViewById<ImageView>(R.id.iv_list_icon_select_car)
        val manufacturer_model = itemView.findViewById<TextView>(R.id.tv_manufacturer_model)
    }

}