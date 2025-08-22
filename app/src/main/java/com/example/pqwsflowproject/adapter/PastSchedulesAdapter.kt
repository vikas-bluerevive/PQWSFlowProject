package com.example.pqwsflowproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.R
import com.example.pqwsflowproject.model.PastItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PastSchedulesAdapter(var arrayPast: ArrayList<PastItem>) : RecyclerView.Adapter<PastSchedulesAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var switch :Switch = itemView.findViewById(R.id.switch1)
        var dayText : TextView = itemView.findViewById<TextView>(R.id.textView12)
        var timeText :TextView = itemView.findViewById<TextView>(R.id.textView41)
        var  tankText : TextView = itemView.findViewById<TextView>(R.id.textView14)
        var textArea :TextView = itemView.findViewById<TextView>(R.id.textView42)

        fun getDayAndTime(dateString: String?){

            var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date: Date? = null
            var currentTime :String? =null
            try {
                date = format.parse(dateString)

                println(date)
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            var calendar = Calendar.getInstance();

            calendar.setTime(date);

            var days = arrayOf( "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" )

            var day = days[calendar.get(Calendar.DAY_OF_WEEK)]

            dayText.setText(day)
            currentTime = SimpleDateFormat("HH:mm:a", Locale.getDefault()).format(date)
            timeText.setText(currentTime)




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_tile, null)
        return PastSchedulesAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayPast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.switch.visibility = View.GONE

        holder.getDayAndTime(arrayPast.get(position).time)

        holder.tankText.setText(arrayPast.get(position).targetTankName+" ,")
        holder.textArea.setText(arrayPast.get(position).targetTankLocation)
    }
}