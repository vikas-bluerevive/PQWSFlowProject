package com.example.pqwsflowproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.R
import com.example.pqwsflowproject.adapter.TankAdapter.ViewHolder

class TimeSlotAndWaterLevelAdapter: RecyclerView.Adapter<TimeSlotAndWaterLevelAdapter.ViewHolder>() {
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

    }

    override fun getItemCount(): Int {
        return 24
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}