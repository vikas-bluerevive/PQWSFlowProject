package com.example.pqwsflowproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.Interface.TileClick
import com.example.pqwsflowproject.R
import com.example.pqwsflowproject.TankScheduleActivity
import com.example.pqwsflowproject.model.TanKData

class TankAdapter( var tankArrayList: ArrayList<TanKData>) : RecyclerView.Adapter<TankAdapter.ViewHolder>() {
    private var tileClick:TileClick? =null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var layout : ConstraintLayout = itemView.findViewById(R.id.constraint)
        var tankName : TextView = itemView.findViewById(R.id.textView10)
        var currentLevel :TextView = itemView.findViewById(R.id.textView39)
        var tankImage :ImageView = itemView.findViewById(R.id.tankImage)


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
       return tankArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tankName.setText(tankArrayList.get(position).tank)
        holder.currentLevel.setText("Current Level "+ tankArrayList.get(position).level + " %")

        holder.tankImage.setImageResource(tankArrayList.get(position).ImageResId)

        holder.layout.setOnClickListener{
           // tileClick?.tileClick(true)

            val intent =     Intent(holder.layout.context, TankScheduleActivity::class.java)

            ContextCompat.startActivity(holder.layout.context, intent, null)
        }
    }


}