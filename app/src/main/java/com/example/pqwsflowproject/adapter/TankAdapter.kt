package com.example.pqwsflowproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.Interface.TileClick
import com.example.pqwsflowproject.MainActivity
import com.example.pqwsflowproject.R
import com.example.pqwsflowproject.TankScheduleActivity
import com.example.pqwsflowproject.TankSchedulerFragment

class TankAdapter: RecyclerView.Adapter<TankAdapter.ViewHolder>() {
    private var tileClick:TileClick? =null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var layout : ConstraintLayout = itemView.findViewById(R.id.constraint)


    }
    fun setInterface(tileClick: TileClick){
       this.tileClick = tileClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.tank_tile, null)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
       return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layout.setOnClickListener{
           // tileClick?.tileClick(true)

            val intent =     Intent(holder.layout.context, TankScheduleActivity::class.java)

            ContextCompat.startActivity(holder.layout.context, intent, null)
        }
    }


}