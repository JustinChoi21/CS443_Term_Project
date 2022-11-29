package edu.umb.cs443termproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.HistoryItems

class HistoryItemAdapter(val historyList: ArrayList<HistoryItems>) : RecyclerView.Adapter<HistoryItemAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false)
        // return CustomViewHolder(view)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{
                val curPos : Int = adapterPosition
                val historyItem : HistoryItems = historyList.get(curPos)
                Toast.makeText(parent.context,
                    "type: " + historyItem.eventType + " / description : " + historyItem.eventDescription,
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    // called when we scroll or use the list
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.icon.setImageResource(historyList.get(position).icon)
        holder.eventType.text = historyList.get(position).eventType
        holder.eventDescription.text = historyList.get(position).eventDescription
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val icon = itemView.findViewById<ImageView>(R.id.iv_list_icon_history)
        val eventType = itemView.findViewById<TextView>(R.id.tv_event_type)
        val eventDescription = itemView.findViewById<TextView>(R.id.tv_event_description)
    }

}