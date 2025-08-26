package com.example.pqwsflowproject.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.pqwsflowproject.R


object CommonFunction {
    var mDialogProgress: AlertDialog? = null
    fun showProgressBar(activity: Activity, message: String) {



        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setCancelable(false)
        val view = LayoutInflater.from(activity).inflate(R.layout.view_progress_dialog, null)
        val textView = view.findViewById<TextView>(R.id.textView)
        textView.setText(message)
        alertDialog.setView(view)
        mDialogProgress = alertDialog.create()
        mDialogProgress!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if(mDialogProgress!=null) {
            if (!mDialogProgress!!.isShowing && !activity.isFinishing) {
                mDialogProgress?.show()
            }
        }


        val lp = WindowManager.LayoutParams()
        mDialogProgress!!.window.also {
            it!!.setGravity(Gravity.CENTER)
            lp.copyFrom(it.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            it.attributes = lp
        }
    }

    fun hideProgressBar() {
        if (mDialogProgress != null) {
            mDialogProgress!!.dismiss()
        }
    }
    fun isNetworkConnected(context : Context) : Boolean{
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.getActiveNetworkInfo()
        val isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()
        return isConnected
    }
}