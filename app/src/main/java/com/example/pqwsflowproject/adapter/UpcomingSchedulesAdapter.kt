package com.example.pqwsflowproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.R
import com.example.pqwsflowproject.model.UpcomingItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class UpcomingSchedulesAdapter(var arrayUpcoming: ArrayList<UpcomingItem>) : RecyclerView.Adapter<UpcomingSchedulesAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
           var sdf =SimpleDateFormat("HH:mm aa", Locale.getDefault())
           currentTime = sdf.format(date)
           //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
           timeText.setText(currentTime)




       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_tile, null)
        return UpcomingSchedulesAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return arrayUpcoming.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getDayAndTime(arrayUpcoming.get(position).time)
        holder.tankText.setText(arrayUpcoming.get(position).targetTankName + " ,")
        holder.textArea.setText(arrayUpcoming.get(position).targetTankLocation)
    }
}