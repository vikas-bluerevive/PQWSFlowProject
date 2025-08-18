package com.example.pqwsflowproject

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
import com.github.dhaval2404.imagepicker.ImagePicker

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

    private  lateinit var mProfileUri :Uri

    //private  var prefs : PreferenceManager ? = null

    private lateinit var pref: SharedPreferences


    private  var areaCodeAdapter : ArrayAdapter<CharSequence>? = null
    private var  sourceTankCodeAdapter: ArrayAdapter<CharSequence>? = null
    private var supplyTankCodeAdapter : ArrayAdapter<CharSequence>? = null

    var tileClick :TileClick?=null


   ;

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

         //prefs = PreferenceManager.getDefaultSharedPreferences(activity) as PreferenceManager?
        pref = activity?.getSharedPreferences("PrefMode", MODE_PRIVATE)!!;
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.getLocations()
        areaArray.add("Select Area")
        sourceTankArray.add("Select Source Tank")
        supplyTankArray.add("Select Supply Tank")

        mainActivityViewModel.locationRes.observe(viewLifecycleOwner, Observer {
          var locRes :LocationResponse? = it
            locRes?.let {

                contentLocation = it.data?.content as MutableList<ContentItem3>
                areaArray.clear()
                areaArray.add("Select Area")
                if(contentLocation.isEmpty()== false){

                    for(items in contentLocation){
                        items.district?.let { areaArray.add(it) }

                    }
                    activity?.runOnUiThread {

                        areaCodeAdapter?.notifyDataSetChanged()
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

                    activity?.runOnUiThread {

                        sourceTankCodeAdapter?.notifyDataSetChanged()
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
                          R.drawable.tank_image2,
                           value.id
                      )
                  }else {
                       tankItem = TanKData(
                          value.name,
                          value.type,
                          value.currentLevel,
                          R.drawable.tank_image,
                           value.id
                      )
                  }
                  tankArrayList.add(tankItem)

                   value.name?.let { it1 ->  supplyTankArray.add(it1) }


               }
               activity?.runOnUiThread {

                   supplyTankCodeAdapter?.notifyDataSetChanged()
               }
           }
          var tankAdapter = TankAdapter(tankArrayList,pref)
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
        areaCodeAdapter = activity?.let {
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
        sourceTankCodeAdapter = activity?.let {
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
                    if (::contentSourceTank.isInitialized) {
                     var sourceid =   contentSourceTank.get(p2-1).id
                        sourceid?.let { mainActivityViewModel.getSupplyTank(it) }
                        sourceid?.let { pref.edit().putInt("sourceId",it) }?.commit()
                    }


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

         supplyTankCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                supplyTankArray as List<CharSequence>
            )
        }

        binding.spinner4.adapter = supplyTankCodeAdapter

         binding.spinner4.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
             override fun onItemSelected(
                 p0: AdapterView<*>?,
                 p1: View?,
                 p2: Int,
                 p3: Long
             ) {
                 if (::contentSupplyTank.isInitialized) {
                     var supplyid =   contentSupplyTank.get(p2-1).id

                     supplyid?.let { pref.edit().putInt("supplyId",it) }?.commit()
                 }
             }

             override fun onNothingSelected(p0: AdapterView<*>?) {

             }

         })
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

        var url = pref.getString("ImageUri","")
//        var uri : Uri = url as Uri
        val imageUri = Uri.parse(url)

        binding.imageView1.setImageURI(imageUri)

        binding.imageView1.setOnClickListener {
           // galleryLauncher.launch("image/*")

            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.materialButton3.setOnClickListener {

            val intent =     Intent( binding.materialButton3.context, TankScheduleActivity::class.java)

            ContextCompat.startActivity( binding.materialButton3.context, intent, null)
        }





                /* @Override
                 public void onActivityResult(Uri o) {
                     if (o == null) {
                         Toast.makeText(MainActivity.this, "No image Selected", Toast.LENGTH_SHORT)
                             .show();
                     } else {
                         Glide.with(getApplicationContext()).load(o).into(imageView);
                     }
                 }*/
//});




    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                 mProfileUri = fileUri
                pref.edit().putString("ImageUri", mProfileUri.toString()).commit()
                binding.imageView1.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }



    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try{
            activity?.let { it1 -> Glide.with(it1) }?.load(it)?.into(binding.imageView1);
            pref.edit().putString("ImageUri", galleryUri.toString() ).commit()
            // binding.image.setImageURI(galleryUri)
        }catch(e:Exception){
            e.printStackTrace()
        }

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