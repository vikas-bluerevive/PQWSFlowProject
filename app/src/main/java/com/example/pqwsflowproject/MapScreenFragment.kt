package com.example.pqwsflowproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.R
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import com.example.pqwsflowproject.databinding.MapScreenBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapScreenFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : MapScreenBinding

    private var googleMap: GoogleMap?=null
    private var mMap: MapView? = null
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
        binding = MapScreenBinding.inflate(inflater,container,false)
        mMap = binding.mapview as MapView
        mMap?.onCreate(savedInstanceState)
        mMap?.getMapAsync(this)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMap?.onPause()
    }
    override fun onStart() {
        super.onStart()
        mMap?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMap?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMap?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMap?.onLowMemory()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

      /*  val mapFragment = childFragmentManager.findFragmentById(binding.mapLinearContainer.id) as? SupportMapFragment
        mapFragment?.getMapAsync(this)*/

        val areaCode = arrayOf("Select Area", "Area 1", "Area2")
        var areaCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                areaCode
            )
        }
        binding.spinner2.adapter = areaCodeAdapter
        val smartBoxCode = arrayOf("Select Smart Box", "SmartBox1", "SmartBox2")

        var smartBoxCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                smartBoxCode
            )
        }
        binding.spinner3.adapter = smartBoxCodeAdapter



    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.getUiSettings().setZoomControlsEnabled(true)
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.addMarker(MarkerOptions().position(LatLng(31.68,76.52)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(31.68,76.52), 40F))
        googleMap.addMarker(MarkerOptions().position(LatLng(31.690783,76.517715)))
        var polyline = googleMap.addPolyline(PolylineOptions()
            .add(LatLng(31.68,76.52) , LatLng(31.690783,76.517715))
            .width(15F)
            .color(android.graphics.Color.RED))

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}