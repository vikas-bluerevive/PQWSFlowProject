package com.example.pqwsflowproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pqwsflowproject.model.DeviceLocationResponse
import com.example.pqwsflowproject.model.DevicesResponse
import com.example.pqwsflowproject.model.LocationDetails
import com.example.pqwsflowproject.model.LocationResponse
import com.example.pqwsflowproject.model.NotificationResponse
import com.example.pqwsflowproject.model.Schedule
import com.example.pqwsflowproject.model.ScheduleSucessResponse
import com.example.pqwsflowproject.model.SchedulesResponse
import com.example.pqwsflowproject.model.SourceTankResponse
import com.example.pqwsflowproject.model.SupplyTankResponse
import com.example.pqwsflowproject.model.User
import com.example.pqwsflowproject.model.WaterSummaryResponse
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

       var locationRes :MutableLiveData<LocationResponse?> = MutableLiveData()
       var sourceTankRes :MutableLiveData<SourceTankResponse?> = MutableLiveData()
       var supplyTankRes :MutableLiveData<SupplyTankResponse?> = MutableLiveData()
       var  user : MutableLiveData<User> = MutableLiveData()
       var result : MutableLiveData<DirectionsResult> = MutableLiveData()

       var notificationRes : MutableLiveData<NotificationResponse> = MutableLiveData()
        var createSchedule : MutableLiveData<ScheduleSucessResponse> = MutableLiveData()
       var createInstantSchedule : MutableLiveData<ScheduleSucessResponse> = MutableLiveData()

       var schedulesResponse  : MutableLiveData<SchedulesResponse> = MutableLiveData()

       var devicesResponse : MutableLiveData<DevicesResponse> = MutableLiveData()
       var deviceStatusAndResponse : MutableLiveData<DeviceLocationResponse> = MutableLiveData()
        var waterSummaryRes : MutableLiveData<WaterSummaryResponse>   = MutableLiveData()

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
                           val res : LocationResponse?  =api.getLocation(0,10).body()
                            res?.let{
                                   locationRes.postValue(it)
                            }

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
                            val res : SourceTankResponse? =api.getSourceTank(0,10,locationId).body()
                            res?.let{
                                   sourceTankRes.postValue(it)
                            }

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
                            val res =api.getSupplyTank(0,10,sourceTankId).body()
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

       fun createScedule(sourceId :Int , supplyId:Int,scheduleTime:String){

              val schedule = Schedule(sourceId,supplyId,scheduleTime)

              progressBar.value= true
              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            val res =api.createSchedule(schedule)
                            createSchedule.postValue(res.body())
                            //supplyTankRes.postValue(res)
                            viewModelScope.launch(Dispatchers.Main){
                                   progressBar.value= false
                            }
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

       fun createInstantSchedule(sourceId:Int ,supplyId:Int){
              val body = mapOf(
                     "sourceDeviceId" to sourceId,
                     "targetDeviceId" to supplyId
              )
              progressBar.value= true

              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            val res =api.createInstantSchedule(body)
                            //supplyTankRes.postValue(res)
                            createInstantSchedule.postValue(res.body())

                            viewModelScope.launch(Dispatchers.Main){
                                   progressBar.value= false
                            }
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

       fun getAllSchedule(sourceId: Int,supplyId: Int){

              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            val res =api.getAllSchedules(sourceId,supplyId)
                            schedulesResponse.postValue(res.body())

                           // sourceTankRes.postValue(res)
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


       fun getDeviceByCity(city:String){
              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            val res =api.getDevicesByCity(city,0,20)
                            devicesResponse.postValue(res.body())

                            // sourceTankRes.postValue(res)
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
       fun getWaterSummary(deviceId:Int,date:String){

              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            val res =api.getWaterSummary(deviceId,date)
                            waterSummaryRes.postValue(res.body())

                            // sourceTankRes.postValue(res)
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

       fun getDeviceLocationAndStatus(deviceId : String){
              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            val res =api.getDeviceLocationAndStatus(deviceId)
                            deviceStatusAndResponse.postValue(res.body())

                            // sourceTankRes.postValue(res)
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

       fun getNotifications(){
              try {
                     viewModelScope.launch(Dispatchers.IO) {
                            val res =api.getNotifications(0,10)
                            // sourceTankRes.postValue(res)
                            notificationRes.postValue(res.body())
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