package com.example.pqwsflowproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqwsflowproject.Interface.TileClick
import com.example.pqwsflowproject.adapter.TankAdapter
import com.example.pqwsflowproject.databinding.DashboardScreenBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DashBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class DashBoardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : DashboardScreenBinding

     var tileClick :TileClick?=null

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
        binding = DashboardScreenBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val areaCode = arrayOf("Select Area", "Area1", "Area2", "Area3")
        var areaCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                areaCode
            )
        }
        binding.spinner2.adapter = areaCodeAdapter
        val sourceTankCode = arrayOf("Select Source Tank", "Tank1", "Tank2", "Tank3")
        var sourceTankCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                sourceTankCode
            )
        }
        binding.spinner3.adapter = sourceTankCodeAdapter

        binding.spinner3.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != 0){

                    binding.view3.visibility = View.VISIBLE
                    binding.view4.visibility = View.VISIBLE
                    binding.textView17.visibility = View.VISIBLE
                    binding.recyclerTankersListing.visibility = View.VISIBLE
                }else{

                    binding.view3.visibility = View.GONE
                    binding.view4.visibility = View.GONE
                    binding.textView17.visibility = View.GONE
                    binding.recyclerTankersListing.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
            );


        val supplyTankCode = arrayOf("Select Supply Tank", "Tank1", "Tank2", "Tank3")

        var supplyTankCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                supplyTankCode
            )
        }

        binding.spinner4.adapter = supplyTankCodeAdapter


        var adapter = TankAdapter()
        adapter.setInterface(object  : TileClick{
            override fun tileClick(click: Boolean) {
               if(click){
                   tileClick?.tileClick(click)



               }
            }
        })
        binding.recyclerTankersListing.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
       binding.recyclerTankersListing.adapter = adapter


    }
    open fun TileClick(tileClicked: TileClick){
        tileClick = tileClicked
    }

    companion object {



        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashBoardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}