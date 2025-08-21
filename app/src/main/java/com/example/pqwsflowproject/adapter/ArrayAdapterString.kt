package com.example.pqwsflowproject.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pqwsflowproject.R


class ArrayAdapterStringAdapter(var _context: Context, var _resource:Int,
                                        var _items: List<String>) : ArrayAdapter<String>(_context,_resource) {
    var recurso: Int
    var tf: Typeface

    init {
        recurso=_resource;
        tf=Typeface.createFromAsset(_context.getAssets(),"font/digital-7.ttf");
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
       //var  spinner_text : TextView =convertView.findViewById(R.id.text1);
       // spinner_text.setTypeface(tf);
    }


}