package com.example.pqwsflowproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pqwsflowproject.utils.JsonParsor
import com.google.android.gms.common.api.ApiException
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivityViewModel : ViewModel(){

       var  jsonString : MutableLiveData<String> = MutableLiveData()
       var result : MutableLiveData<DirectionsResult> = MutableLiveData()

       fun getResults( origin: String,
                       destination: String,
                       mode: TravelMode){

              viewModelScope.launch(Dispatchers.IO) {

                     result.postValue(getDirectionsDetails(origin,destination,mode))


              }




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


}