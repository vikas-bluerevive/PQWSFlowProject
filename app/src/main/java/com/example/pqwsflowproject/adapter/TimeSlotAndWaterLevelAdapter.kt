package com.example.pqwsflowproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.R
import com.example.pqwsflowproject.model.FlowValues

class TimeSlotAndWaterLevelAdapter(var hoursFlowItems: ArrayList<FlowValues>) : RecyclerView.Adapter<TimeSlotAndWaterLevelAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.time_slotandwater_leveltile, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
         holder.timeSlot.setText(hoursFlowItems.get(position).timeSlot)
        holder.waterLevel.setText(""+hoursFlowItems.get(position).waterLevel)
    }

    override fun getItemCount(): Int {
        return hoursFlowItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var timeSlot : TextView = itemView.findViewById<TextView>(R.id.timeslot)
        var waterLevel : TextView = itemView.findViewById<TextView>(R.id.waterlevel)

    }
}