package com.example.pqwsflowproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pqwsflowproject.model.LocationDetails
import com.example.pqwsflowproject.model.LocationResponse
import com.example.pqwsflowproject.model.SourceTankResponse
import com.example.pqwsflowproject.model.SupplyTankResponse
import com.example.pqwsflowproject.model.User
import com.example.pqwsflowproject.network.Api
import com.example.pqwsflowproject.network.Repository
import com.example.pqwsflowproject.utils.JsonParsor
import com.example.pqwsflowproject.utils.NoInternetException
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

class MainActivityViewModel : BaseViewModel(){

       var  jsonString : MutableLiveData<String> = MutableLiveData()

       var  locations : MutableLiveData<List<LocationDetails>> = MutableLiveData()

       var locationRes :MutableLiveData<LocationResponse> = MutableLiveData()
       var sourceTankRes :MutableLiveData<SourceTankResponse> = MutableLiveData()
       var supplyTankRes :MutableLiveData<SupplyTankResponse> = MutableLiveData()
       var  user : MutableLiveData<User> = MutableLiveData()
       var result : MutableLiveData<DirectionsResult> = MutableLiveData()

       var api : Api
       init {
           api = Repository.retrofit
       }

       fun getResults( origin: String,
                       destination: String,
                       mode: TravelMode){

              viewModelScope.launch(Dispatchers.IO) {
                     result.postValue(getDirectionsDetails(origin,destination,mode))
              }
       }
       fun getLocations(){
              try {
                     viewModelScope.launch(Dispatchers.IO) {
                           var res =api.getLocation(0,10).body()
                            locationRes.postValue(res)
                     }

                     /*val response =
                         respository.socialLogin(name, email, image, phone, instagramId, googleId, fbId)*/
              } catch (e: com.example.pqwsflowproject.utils.ApiException) {
                     progressBar.value = false
                     feedBackMessage.value = e.message!!

              } catch (e: NoInternetException) {

                     progressBar.value = false
                     feedBackMessage.value = e.message!!

              }

       }

       fun getSourceTank(locationId :Int){

              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            var res =api.getSourceTank(0,10,locationId).body()
                            sourceTankRes.postValue(res)
                     }

                     /*val response =
                         respository.socialLogin(name, email, image, phone, instagramId, googleId, fbId)*/
              } catch (e: com.example.pqwsflowproject.utils.ApiException) {
                     progressBar.value = false
                     feedBackMessage.value = e.message!!

              } catch (e: NoInternetException) {

                     progressBar.value = false
                     feedBackMessage.value = e.message!!

              }

       }

       fun getSupplyTank(sourceTankId:Int){
              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            var res =api.getSupplyTank(0,10,sourceTankId).body()
                            supplyTankRes.postValue(res)
                     }

                     /*val response =
                         respository.socialLogin(name, email, image, phone, instagramId, googleId, fbId)*/
              } catch (e: com.example.pqwsflowproject.utils.ApiException) {
                     progressBar.value = false
                     feedBackMessage.value = e.message!!

              } catch (e: NoInternetException) {

                     progressBar.value = false
                     feedBackMessage.value = e.message!!

              }


       }

       fun getUser(userId :String){
              try {
                     viewModelScope.launch(Dispatchers.IO) {

                            user.value = api.getUser(userId).body()
                     }

                     /*val response =
                         respository.socialLogin(name, email, image, phone, instagramId, googleId, fbId)*/
              } catch (e: com.example.pqwsflowproject.utils.ApiException) {
                     progressBar.value = false
                     feedBackMessage.value = e.message!!

              } catch (e: NoInternetException) {

                     progressBar.value = false
                     feedBackMessage.value = e.message!!

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