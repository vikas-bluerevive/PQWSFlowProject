package com.example.pqwsflowproject.network

import com.example.pqwsflowproject.model.CreatedUser
import com.example.pqwsflowproject.model.LocationDetails
import com.example.pqwsflowproject.model.LocationResponse
import com.example.pqwsflowproject.model.LoginResponse
import com.example.pqwsflowproject.model.LoginResponse2
import com.example.pqwsflowproject.model.NotificationResponse
import com.example.pqwsflowproject.model.Schedule
import com.example.pqwsflowproject.model.ScheduleSucessResponse
import com.example.pqwsflowproject.model.SchedulesResponse
import com.example.pqwsflowproject.model.SourceTankResponse
import com.example.pqwsflowproject.model.SummaryResponse
import com.example.pqwsflowproject.model.SupplyTankResponse
import com.example.pqwsflowproject.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {


    @POST("auth/login")
    suspend fun Login( @Body body: Map<String, String>): Response<LoginResponse2>

    @GET("users/{page}/{size}")
    suspend fun getUsers(@Path("page")id:Int?,@Path("size")size:Int?):Response<List<User>>


    @GET("users")
    suspend fun getUser(@Url userId:String):Response<User>

    @FormUrlEncoded
    @POST("users")
    suspend fun creatUser(@Field("username") username:String,@Field("email") email:String , @Field("password") password:String,@Field("phone") phone:String,@Field("role") role:String,@Field("isActive") isActive:Boolean) :Response<CreatedUser>

   /* @GET("users")
    suspend fun getLocation():Response<List<LocationDetails>>*/
    @GET("locations")
    suspend fun getLocation( @Query("page") page: Int?,@Query("size") size: Int?):Response<LocationResponse>

   @POST("flow/schedules")
   suspend fun createSchedule(@Body schedule: Schedule) : Response<ScheduleSucessResponse>

    @POST("flow/instant")
    suspend fun createInstantSchedule(@Body body: Map<String, Int>) : Response<ScheduleSucessResponse>

    @GET("notification")
    suspend fun getNotifications(@Query("page") page: Int?,@Query("size") size: Int?) : Response<NotificationResponse>

    @GET("flow/schedules")
    suspend fun getAllSchedules(@Query("sourceTankId") sourceTankId: Int?,@Query("targetTankId") targetTankId: Int?) : Response<SchedulesResponse>

    @GET("tank/source")
    suspend fun getSourceTank(@Query("page") page: Int?,@Query("size") size: Int?,@Query("locationId") locId :Int?):Response<SourceTankResponse>

    @GET("flow/{deviceId}/summary")
    suspend fun getSummary(@Path("deviceId")deviceId :Int,@Query("date") date :Int) : Response<SummaryResponse>
    @GET("tank/supply")
    suspend fun getSupplyTank(@Query("page") page: Int?,@Query("size") size: Int?,@Query("sourceTankId") sourceTankId :Int?):Response<SupplyTankResponse>

    @GET("tank/{page}/{size}/{locationId}")
    suspend fun getTankByLocation( @Path("page")id:Int?,@Path("size")size:Int?,@Path("locationId")locId:Int?):Response<ResponseBody>

}