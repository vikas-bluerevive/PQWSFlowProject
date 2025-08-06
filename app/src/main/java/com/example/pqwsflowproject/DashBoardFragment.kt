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
import com.example.pqwsflowproject.Interface.TileClick
import com.example.pqwsflowproject.adapter.TankAdapter
import com.example.pqwsflowproject.databinding.DashboardScreenBinding
import com.example.pqwsflowproject.model.ContentItem
import com.example.pqwsflowproject.model.ContentItem3
import com.example.pqwsflowproject.model.LocationResponse
import com.example.pqwsflowproject.model.SourceTankResponse
import com.example.pqwsflowproject.model.SupplyTankResponse
import com.example.pqwsflowproject.model.TanKData
import com.example.pqwsflowproject.viewmodels.MainActivityViewModel

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

    private lateinit var mainActivityViewModel: MainActivityViewModel
     private var  locationContents : MutableList<ContentItem3> = mutableListOf()
    private lateinit var contentLocation : MutableList<ContentItem3>
    private lateinit var contentSourceTank : MutableList<ContentItem>
    private lateinit var contentSupplyTank : MutableList<ContentItem>
    private var areaArray : ArrayList<String> = ArrayList()
    private var sourceTankArray : ArrayList<String> = ArrayList()
    private var tankArrayList : ArrayList<TanKData> = ArrayList()

    private var supplyTankArray : ArrayList<String> = ArrayList()
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
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.getLocations()
        areaArray.add("Select Area")
        sourceTankArray.add("Select Source Tank")
        supplyTankArray.add("Select Supply Tank")

        mainActivityViewModel.locationRes.observe(viewLifecycleOwner, Observer {
          var locRes :LocationResponse? = it
            locRes?.let {

                contentLocation = it.data?.content as MutableList<ContentItem3>
                if(contentLocation.isEmpty()== false){

                    for(items in contentLocation){
                        items.district?.let { areaArray.add(it) }

                    }
                }

            }

        })


        mainActivityViewModel.sourceTankRes.observe(viewLifecycleOwner,Observer{
            var sourceTankResponse :SourceTankResponse? = it
            sourceTankResponse?.let {
                 contentSourceTank = it.data?.content as MutableList<ContentItem>
                sourceTankArray.clear()
                sourceTankArray.add("Select Source Tank")
                if(contentSourceTank.isEmpty()== false){

                    for(items in contentSourceTank){
                        items.name?.let { it1 -> sourceTankArray.add(it1) }


                    }
                }

            }
        })

   mainActivityViewModel.supplyTankRes.observe(viewLifecycleOwner, Observer {
       var supplyTankResponse :SupplyTankResponse? = it
       supplyTankResponse?.let {






           contentSupplyTank =  it.data?.content as MutableList<ContentItem>
           supplyTankArray.clear()
           supplyTankArray.add("Select Supply Tank")
           if(contentSupplyTank.isEmpty()== false){
               tankArrayList.clear()
              for((index, value) in contentSupplyTank.withIndex())
               {
                   var tankItem : TanKData
                  if(index % 2 == 0){
                       tankItem = TanKData(
                          value.name,
                          value.type,
                          value.currentLevel,
                          R.drawable.tank_image2
                      )
                  }else {
                       tankItem = TanKData(
                          value.name,
                          value.type,
                          value.currentLevel,
                          R.drawable.tank_image
                      )
                  }
                  tankArrayList.add(tankItem)

                   value.name?.let { it1 ->  supplyTankArray.add(it1) }


               }
           }
          var tankAdapter = TankAdapter(tankArrayList)
           binding.recyclerTankersListing.layoutManager = LinearLayoutManager(activity,
               LinearLayoutManager.VERTICAL,false)
           binding.recyclerTankersListing.adapter = tankAdapter


           binding.view3.visibility = View.VISIBLE
           binding.view4.visibility = View.VISIBLE
           binding.textView17.visibility = View.VISIBLE
           binding.recyclerTankersListing.visibility = View.VISIBLE
       }
   })



        val areaCode = arrayOf("Select Area", "Area1", "Area2", "Area3")
        var areaCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                areaArray as List<CharSequence>
            )
        }
        binding.spinner2.adapter = areaCodeAdapter


        binding.spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               if(p2!=0){
                   contentLocation.get(p2 -1).id?.let { mainActivityViewModel.getSourceTank(it) }
               }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })
        val sourceTankCode = arrayOf("Select Source Tank", "Tank1", "Tank2", "Tank3")
        var sourceTankCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                sourceTankArray as List<CharSequence>
            )
        }
        binding.spinner3.adapter = sourceTankCodeAdapter

        binding.spinner3.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != 0){
                     mainActivityViewModel.getSupplyTank(3)

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
                supplyTankArray as List<CharSequence>
            )
        }

        binding.spinner4.adapter = supplyTankCodeAdapter


        /*var adapter = TankAdapter(tankArrayList)
        adapter.setInterface(object  : TileClick{
            override fun tileClick(click: Boolean) {
               if(click){
                   tileClick?.tileClick(click)



               }
            }
        })
        binding.recyclerTankersListing.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
       binding.recyclerTankersListing.adapter = adapter*/


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