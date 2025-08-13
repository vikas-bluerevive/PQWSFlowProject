package com.example.pqwsflowproject.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.pqwsflowproject.R
import com.example.pqwsflowproject.model.ContentItem5
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import java.text.ParseException
import java.text.SimpleDateFormat

class AlertAdapter(
    var context1: Context?,
    var viewLifecycleOwner: LifecycleOwner,
    var arrayContent: ArrayList<ContentItem5>
) : RecyclerView.Adapter<AlertAdapter.ViewHolder>() {
     var context = context1
    class ViewHolder(var itemView: View,var context: Context?,var viewLifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {
        var alertText: TextView = itemView.findViewById<TextView>(R.id.textView18)
        var tanktext: TextView = itemView.findViewById<TextView>(R.id.textView19)
        var tank2Text: TextView = itemView.findViewById<TextView>(R.id.textView40)
        var agoText: TextView = itemView.findViewById<TextView>(R.id.ago)

        var alertTile: ConstraintLayout =
            itemView.findViewById<ConstraintLayout>(R.id.alertConstraint)


        fun createBallonn(textSet : String): Balloon ?{
            val balloon = context?.let { Balloon.Builder(it) }
                ?.setWidthRatio(1.0f)
                ?.setHeight(BalloonSizeSpec.WRAP)
                ?.setText(textSet)
                ?.setTextColorResource(R.color.white)
                ?.setTextSize(15f)

                ?.setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                ?.setArrowSize(10)
                ?.setArrowPosition(0.5f)
                ?.setPadding(12)
                ?.setCornerRadius(8f)
                ?.setBackgroundColorResource(R.color.blue)
                ?.setBalloonAnimation(BalloonAnimation.ELASTIC)
                ?.setLifecycleOwner(viewLifecycleOwner)
                ?.build()
            return balloon
        }


        fun getLongAgoText(dateString:String){


            val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
            try {
                val time: Long = sdf.parse(dateString).getTime()
                val now = System.currentTimeMillis()
                val ago: CharSequence? =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                agoText.setText(ago)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.alert_tile, null)
        return AlertAdapter.ViewHolder(view,context, viewLifecycleOwner = viewLifecycleOwner)
    }

    override fun getItemCount(): Int {
        return arrayContent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var balloon = holder.createBallonn(arrayContent.get(position).message.toString())

        holder.alertText.setText(arrayContent.get(position).type.toString())
        holder.tanktext.setText(arrayContent.get(position).sourceBoxId.toString()+"-")
        holder.tank2Text.setText(arrayContent.get(position).sourceDeviceName.toString())

        arrayContent.get(position).time?.let { holder.getLongAgoText(it) }


        holder.alertTile.setOnClickListener{
           // balloon?.showAlignTop()
            balloon?.showAsDropDown(holder.tanktext)
        }

    }
}