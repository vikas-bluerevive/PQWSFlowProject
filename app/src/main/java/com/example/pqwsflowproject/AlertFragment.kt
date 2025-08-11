package com.example.pqwsflowproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqwsflowproject.adapter.AlertAdapter
import com.example.pqwsflowproject.databinding.AlertScreenBinding
import com.example.pqwsflowproject.model.ContentItem5
import com.example.pqwsflowproject.model.NotificationResponse
import com.example.pqwsflowproject.viewmodels.MainActivityViewModel
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec


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
    private lateinit var mainActivityViewModel: MainActivityViewModel

    private var  arrayContent : ArrayList<ContentItem5> = ArrayList()

    private var arrayUrgent : ArrayList<ContentItem5> = ArrayList()

    private var arrayMaitainess : ArrayList<ContentItem5> = ArrayList()


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
        val balloon = createBallon()
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getNotifications()

        mainActivityViewModel.notificationRes.observe(viewLifecycleOwner, Observer {
          var notificationResponse : NotificationResponse? = it

            notificationResponse?.let{
                arrayContent = it.data?.content  as ArrayList<ContentItem5>

                var alertAdapter = AlertAdapter(context , viewLifecycleOwner,arrayContent)
                binding.recyclerAlertListing.layoutManager = LinearLayoutManager(activity,
                    LinearLayoutManager.VERTICAL,false)
                binding.recyclerAlertListing.adapter = alertAdapter
            }
        })


        val areaCode = arrayOf("All", "Urgent", "Maintainess")
        var areaCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                areaCode
            )
        }
        binding.spinner2.adapter = areaCodeAdapter



        binding.spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                 if(p2 == 1){
                     for(item in arrayContent){

                         if(item.severity.equals("HIGH")){
                             arrayUrgent.add(item)
                         }
                     }

                     var alertAdapter = AlertAdapter(context , viewLifecycleOwner,arrayUrgent)
                     binding.recyclerAlertListing.layoutManager = LinearLayoutManager(activity,
                         LinearLayoutManager.VERTICAL,false)
                     binding.recyclerAlertListing.adapter = alertAdapter
                 }else if(p2 == 2){

                     for(item in arrayContent){

                         if(item.severity.equals("MEDIUM") || item.severity.equals("LOW")){
                             arrayMaitainess.add(item)
                         }
                     }
                     var alertAdapter = AlertAdapter(context , viewLifecycleOwner,arrayMaitainess)
                     binding.recyclerAlertListing.layoutManager = LinearLayoutManager(activity,
                         LinearLayoutManager.VERTICAL,false)
                     binding.recyclerAlertListing.adapter = alertAdapter



                 }else if(p2 == 0){

                     var alertAdapter = AlertAdapter(context , viewLifecycleOwner,arrayContent)
                     binding.recyclerAlertListing.layoutManager = LinearLayoutManager(activity,
                         LinearLayoutManager.VERTICAL,false)
                     binding.recyclerAlertListing.adapter = alertAdapter
                 }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })

        val areaCode2 = arrayOf("Urgent", "Area 1", "Area2")
        var areaCode2Adapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                areaCode2
            )
        }
        binding.spinner3.adapter = areaCode2Adapter
        val areaCode3 = arrayOf("Maintainess", "Area 1", "Area2")
        var areaCode3Adapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                areaCode3
            )
        }
        binding.spinner4.adapter = areaCode3Adapter



    }

    fun createBallon() : Balloon? {
        val balloon = context?.let { Balloon.Builder(it) }
            ?.setWidthRatio(1.0f)
            ?.setHeight(BalloonSizeSpec.WRAP)
            ?.setText("Edit your profile here!")
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