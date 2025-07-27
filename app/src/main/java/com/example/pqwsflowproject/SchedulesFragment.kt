package com.example.pqwsflowproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqwsflowproject.adapter.AlertAdapter
import com.example.pqwsflowproject.adapter.PastSchedulesAdapter
import com.example.pqwsflowproject.adapter.UpcomingSchedulesAdapter
import com.example.pqwsflowproject.databinding.SchedulesScreenBinding
import com.example.pqwsflowproject.databinding.TankSchedulerScreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SchedulesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SchedulesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : SchedulesScreenBinding
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
        binding = SchedulesScreenBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var adapter = UpcomingSchedulesAdapter()
        binding.recyclerUpcomingListing.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        binding.recyclerUpcomingListing.adapter = adapter


        var adapter2 = PastSchedulesAdapter()
        binding.recyclerPastListing.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        binding.recyclerPastListing.adapter = adapter2

        binding.newschedulebutton.setOnClickListener{
            val intent =     Intent(binding.newschedulebutton.context, TankScheduleActivity::class.java)

            ContextCompat.startActivity(binding.newschedulebutton.context, intent, null)
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SchedulesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SchedulesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}