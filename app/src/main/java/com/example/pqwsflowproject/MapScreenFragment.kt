package com.example.pqwsflowproject

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pqwsflowproject.R.color.teal_700
import com.example.pqwsflowproject.databinding.MapScreenBinding
import com.example.pqwsflowproject.model.ContentItem3
import com.example.pqwsflowproject.model.ContentItem8
import com.example.pqwsflowproject.model.DeviceLocationResponse
import com.example.pqwsflowproject.model.DevicesResponse
import com.example.pqwsflowproject.model.LocationResponse
import com.example.pqwsflowproject.viewmodels.MainActivityViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.android.PolyUtil
import com.google.maps.model.DirectionsResult
import com.google.maps.model.DirectionsRoute
import com.google.maps.model.TravelMode
import org.joda.time.DateTime
import org.json.JSONObject
import java.io.IOException
import java.util.Locale
import java.util.concurrent.TimeUnit


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
    private lateinit var binding: MapScreenBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var googleMap: GoogleMap? = null
    private var mMap: MapView? = null

    private lateinit var contentLocation : MutableList<ContentItem3>

    private lateinit var contentDevices : MutableList<ContentItem8>

    private var areaArray : ArrayList<String> = ArrayList()

    private var arrayDevices : ArrayList<String> = ArrayList()

    private var latitute :Double =0.0
    private var logitute :Double = 0.0

    private lateinit var googleMapp: GoogleMap

    private lateinit var pref: SharedPreferences

    private  var areaCodeAdapter : ArrayAdapter<CharSequence>? = null
    private  var  smartBoxCodeAdapter: ArrayAdapter<CharSequence>? = null

    private val overview = 0
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
        binding = MapScreenBinding.inflate(inflater, container, false)
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





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = activity?.getSharedPreferences("PrefMode", MODE_PRIVATE)!!
        areaArray.add("Select Area")
        arrayDevices.add("Select Smart Boxes")
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getLocations()

        mainActivityViewModel.locationRes.observe(viewLifecycleOwner, Observer {
            val locRes :LocationResponse? = it
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
        binding.spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                if(p2!=0){
                   if(contentLocation.isEmpty()==false) {

                       contentLocation.get(p2-1).city?.let { mainActivityViewModel.getDeviceByCity(it) }


                   }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        })

        mainActivityViewModel.devicesResponse.observe(viewLifecycleOwner, Observer{

            var devicesResponse : DevicesResponse? = it
            devicesResponse?.let{
               arrayDevices.clear()
                arrayDevices.add("Select Smart Boxes")
                contentDevices = it.data?.content as MutableList<ContentItem8>

                for(items in contentDevices){
                    items.deviceId?.let { e -> arrayDevices.add(e) }


                }

                activity?.runOnUiThread {
                    smartBoxCodeAdapter?.notifyDataSetChanged()
                }

            }
        })

       /* mainActivityViewModel.jsonString.observe(viewLifecycleOwner, Observer {
           drawPath(it)
        })*/
        mainActivityViewModel.result.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                googleMap?.let { it1 -> addPolyline(it, it1) }
                googleMap?.let { it1 -> positionCamera(it.routes[overview], it1) }
                googleMap?.let { it1 -> addMarkersToMap(it, it1) }
            }
        })
        val areaCode = arrayOf("Select Area", "Area 1", "Area2")
        areaCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.spinner_dropdown,
                areaArray as List<CharSequence>
            )
        }
        binding.spinner2.adapter = areaCodeAdapter
        val smartBoxCode = arrayOf("Select Smart Box", "SmartBox1", "SmartBox2")

         smartBoxCodeAdapter = activity?.let {
            ArrayAdapter<CharSequence>(
                it,
                R.layout.spinner_dropdown,
                arrayDevices as List<CharSequence>
            )
        }
        binding.spinner3.adapter = smartBoxCodeAdapter

        binding.spinner3.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != 0){
                    mainActivityViewModel.getDeviceLocationAndStatus(arrayDevices.get(p2))
                    binding.mapLinearContainer.visibility = View.VISIBLE
                    binding.view3.visibility = View.VISIBLE

                }else{
                    binding.mapLinearContainer.visibility = View.GONE
                    binding.view3.visibility = View.GONE
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        })

        mainActivityViewModel.deviceStatusAndResponse.observe(viewLifecycleOwner, Observer{
            val deviceStatus : DeviceLocationResponse? = it
            deviceStatus?.let{
            Log.e("DeviceStatus","device "+deviceStatus)
                 latitute = it.data?.latitude!!
                  logitute = it.data?.longitude!!

                var status = it.data?.status
                if(status.equals("ACTIVE")){
                binding.textView4.setText(it.data?.status)
                   /* binding.textView4.setTextColor(android.graphics.Color.parseColor("#000000"))
                            binding.textView6.setTextColor(android.graphics.Color.parseColor("#000000"))
                            binding.textView7.setTextColor(android.graphics.Color.parseColor("#000000"))*/


                    binding.viewContraint.setBackgroundColor(getResources().getColor(R.color.green))

                }else{

                    binding.textView4.setText(it.data?.status)
                    binding.textView4.setTextColor(getResources().getColor(R.color.black))
                    binding.textView4.setTextColor(getResources().getColor(R.color.black))
                    binding.textView4.setTextColor(getResources().getColor(R.color.black))

                    binding.viewContraint.setBackgroundColor(getResources().getColor(R.color.grey2))

                }

               googleMapp.clear()
                googleMapp.addMarker(MarkerOptions().title(it.data?.deviceId)
                    .position(LatLng(latitute, logitute)))
                googleMapp.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitute ,logitute), 18F))
               // googleMapp.addMarker(MarkerOptions().position(LatLng(latitute, logitute)))
            }
        })



     try{

          var geocoder = activity?.let { Geocoder(it, Locale.getDefault()) }
         var  addresses : MutableList<Address>? = geocoder?.getFromLocation(31.68, 76.52, 1)

         val address: Address? = addresses?.get(0)
        var result =  address?.getAddressLine(0) + ", " + address?.getLocality()

        } catch (e:IOException) {
        // TODO Auto-generated catch block
             e.printStackTrace();

          }





        var url = pref.getString("ImageUri","")
//        var uri : Uri = url as Uri
        val imageUri = Uri.parse(url)

        binding.imageView1.setImageURI(imageUri)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMapp = googleMap
      //  googleMap.getUiSettings().setZoomControlsEnabled(true)
      //  googleMap.uiSettings.isScrollGesturesEnabled = true
        setupGoogleMapScreenSettings(googleMap)

       /* val results: DirectionsResult? = getDirectionsDetails(
            "483 George St, Sydney NSW 2000, Australia",
            "182 Church St, Parramatta NSW 2150, Australia",
            TravelMode.DRIVING
        )*/
       /* mainActivityViewModel.getResults("483 George St, Sydney NSW 2000, Australia",
            "182 Church St, Parramatta NSW 2150, Australia",
            TravelMode.DRIVING)*/







      //  makeURL(31.68,76.52,31.690783,76.517715)?.let { mainActivityViewModel.getJsonFromString(it) }
       /* var polyline = googleMap.addPolyline(
            PolylineOptions()
                .add(LatLng(31.68, 76.52), LatLng(31.690783, 76.517715))
                .width(15F)
                .color(android.graphics.Color.RED)
        )*/

    }

    private fun addMarkersToMap(results: DirectionsResult, mMap: GoogleMap) {
        mMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    results.routes.get(overview).legs.get(
                        overview
                    ).startLocation.lat,
                    results.routes.get(overview).legs.get(overview).startLocation.lng
                )
            ).title(results.routes.get(overview).legs.get(overview).startAddress)
        )
        mMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    results.routes.get(overview).legs.get(
                        overview
                    ).endLocation.lat,
                    results.routes.get(overview).legs.get(overview).endLocation.lng
                )
            ).title(results.routes.get(overview).legs.get(overview).startAddress)
                .snippet(getEndLocationTitle(results))
        )
    }

    private fun positionCamera(route: DirectionsRoute, mMap: GoogleMap) {
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    route.legs.get(overview).startLocation.lat,
                    route.legs.get(overview).startLocation.lng
                ), 12f
            )
        )
    }

    private fun addPolyline(results: DirectionsResult, mMap: GoogleMap) {
        val decodedPath: List<LatLng> =
            PolyUtil.decode(results.routes.get(overview).overviewPolyline.getEncodedPath())
        mMap.addPolyline(PolylineOptions().addAll(decodedPath))
    }

    private fun getEndLocationTitle(results: DirectionsResult): String? {
        return "Time :" + results.routes.get(overview).legs.get(overview).duration.humanReadable + " Distance :" + results.routes.get(
            overview
        ).legs.get(overview).distance.humanReadable
    }
    private fun setupGoogleMapScreenSettings(mMap: GoogleMap) {
        mMap.isBuildingsEnabled = true
        mMap.isIndoorEnabled = true
        mMap.isTrafficEnabled = true
        val mUiSettings = mMap.uiSettings
        mUiSettings.isZoomControlsEnabled = true
        mUiSettings.isCompassEnabled = true
        mUiSettings.isMyLocationButtonEnabled = true
        mUiSettings.isScrollGesturesEnabled = true
        mUiSettings.isZoomGesturesEnabled = true
        mUiSettings.isTiltGesturesEnabled = true
        mUiSettings.isRotateGesturesEnabled = true
    }

    private fun getDirectionsDetails(
        origin: String,
        destination: String,
        mode: TravelMode
    ): DirectionsResult? {
        val now = DateTime()
        return try {
            DirectionsApi.newRequest(getGeoContext())
                .mode(mode)
                .origin(origin)
                .destination(destination)
                .departureTime(now)
                .await()
        } catch (e: ApiException) {
            e.printStackTrace()
            null
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getGeoContext(): GeoApiContext? {
        val geoApiContext = GeoApiContext()
        return geoApiContext
            .setQueryRateLimit(3)
            .setApiKey("AIzaSyAsreEPAjoR0X9TVKDRXKnS4mG2Ju9_Jko")
            .setConnectTimeout(1, TimeUnit.SECONDS)
            .setReadTimeout(1, TimeUnit.SECONDS)
            .setWriteTimeout(1, TimeUnit.SECONDS)
    }

    fun drawPath(result: String?) {
        /* if (line != null) {
            myMap.clear()
        }
        myMap.addMarker(
            MarkerOptions().position(endLatLng).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.redpin_marker)
            )
        )
        myMap.addMarker(
            MarkerOptions().position(startLatLng).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.redpin_marker)
            )
        )*/
        try {
            // Tranform the string into a json object
            val json = JSONObject(result)
            val routeArray = json.getJSONArray("routes")
            val routes = routeArray.getJSONObject(0)
            val overviewPolylines = routes
                .getJSONObject("overview_polyline")
            val encodedString = overviewPolylines.getString("points")
            val list: List<LatLng> = decodePoly(encodedString)
            for (z in 0 until list.size - 1) {
                val src = list[z]
                val dest = list[z + 1]
                var polyline = googleMap?.addPolyline(
                    PolylineOptions()
                        .add(
                            LatLng(src.latitude, src.longitude),
                            LatLng(dest.latitude, dest.longitude)
                        )
                        .width(15f).color(android.graphics.Color.RED).geodesic(true)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly: MutableList<LatLng> = ArrayList()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }

    fun makeURL(
        sourcelat: Double, sourcelog: Double, destlat: Double,
        destlog: Double
    ): String? {
        val urlString = StringBuilder()
        urlString.append("http://maps.googleapis.com/maps/api/directions/json")
        urlString.append("?origin=") // from
        urlString.append(java.lang.Double.toString(sourcelat))
        urlString.append(",")
        urlString.append(java.lang.Double.toString(sourcelog))
        urlString.append("&destination=") // to
        urlString.append(java.lang.Double.toString(destlat))
        urlString.append(",")
        urlString.append(java.lang.Double.toString(destlog))
        urlString.append("&sensor=false&mode=driving&alternatives=true")
        return urlString.toString()
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