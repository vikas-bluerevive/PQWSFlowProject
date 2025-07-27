package com.example.pqwsflowproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.R

class PastSchedulesAdapter  : RecyclerView.Adapter<PastSchedulesAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_tile, null)
        return PastSchedulesAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}