package com.example.pqwsflowproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.R
import com.example.pqwsflowproject.databinding.DashboardScreenBinding
import com.example.pqwsflowproject.databinding.TankSchedulerScreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TankSchedulerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TankSchedulerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : TankSchedulerScreenBinding
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
        binding = TankSchedulerScreenBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val instantWaterCode = arrayOf("Instant Water Supply", "true", "false")
        var instantWaterCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                instantWaterCode
            )
        }
        binding.spinner2.adapter = instantWaterCodeAdapter

        val timeScheduleWaterSupplyCode = arrayOf("Time Schedule Water Supply", "time1", "time2","time3")
        var timeScheduleWaterSupplyCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                timeScheduleWaterSupplyCode
            )
        }
        binding.spinner3.adapter = timeScheduleWaterSupplyCodeAdapter

        val waterToBeFilledCode = arrayOf("Select Water to be filled", "water1", "water2","water3")

        var waterToBeFilledCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                waterToBeFilledCode
            )
        }

        binding.spinner4.adapter = waterToBeFilledCodeAdapter


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TankSchedulerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TankSchedulerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}