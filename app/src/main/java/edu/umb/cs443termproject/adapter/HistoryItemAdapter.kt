package edu.umb.cs443termproject.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.HistoryItems
import edu.umb.cs443termproject.fragments.EditFragment
import edu.umb.cs443termproject.fragments.HistoryFragment
import kotlinx.android.synthetic.main.activity_main.*

class HistoryItemAdapter(val historyList: ArrayList<HistoryItems>) : RecyclerView.Adapter<HistoryItemAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false)
        // return CustomViewHolder(view)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{
                val curPos : Int = adapterPosition
                val historyItem : HistoryItems = historyList.get(curPos)

                // replace this fragment to EditFragment with historyItem
                val bundle:Bundle = Bundle()
                bundle.putInt("historyId", historyItem.id)
                val editFragment = EditFragment.newInstance()
                editFragment.arguments = bundle

                val activity = parent.context as AppCompatActivity
                (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragments_frame, editFragment)
                    .addToBackStack(null)
                    .commit()
                (activity as AppCompatActivity).supportActionBar?.title = "Edit/Remove History"
                (activity as AppCompatActivity).bottom_nav.menu.getItem(1).isChecked = true

                // hide floating action button
                val fab = (activity as MainActivity).findViewById<View>(R.id.floatingActionButton)
                fab.visibility = View.GONE
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
        holder.eventDate.text = historyList.get(position).eventDate
        holder.eventDescription.text = historyList.get(position).eventDescription
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val icon = itemView.findViewById<ImageView>(R.id.iv_list_icon_refuel_reminder)
        val eventType = itemView.findViewById<TextView>(R.id.tv_refuel_reminder)
        val eventDate = itemView.findViewById<TextView>(R.id.tv_event_date)
        val eventDescription = itemView.findViewById<TextView>(R.id.tv_event_description)
    }

}