package com.example.pqwsflowproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqwsflowproject.adapter.AlertAdapter
import com.example.pqwsflowproject.databinding.AlertScreenBinding
import com.example.pqwsflowproject.databinding.TankSchedulerScreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlertFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : AlertScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlertScreenBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var alertAdapter = AlertAdapter()
        binding.recyclerAlertListing.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        binding.recyclerAlertListing.adapter = alertAdapter
        val areaCode = arrayOf("All", "Urgent", "Maintainess")
        var areaCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                areaCode
            )
        }
        binding.spinner2.adapter = areaCodeAdapter

        val areaCode2 = arrayOf("Urgent", "Area 1", "Area2")
        var areaCode2Adapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                areaCode2
            )
        }
        binding.spinner3.adapter = areaCode2Adapter
        val areaCode3 = arrayOf("Maintainess", "Area 1", "Area2")
        var areaCode3Adapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                areaCode3
            )
        }
        binding.spinner4.adapter = areaCode3Adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AlertFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}