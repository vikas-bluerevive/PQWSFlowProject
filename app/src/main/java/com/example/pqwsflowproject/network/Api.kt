package com.example.pqwsflowproject.network

import com.example.pqwsflowproject.model.CreatedUser
import com.example.pqwsflowproject.model.LocationDetails
import com.example.pqwsflowproject.model.LoginResponse
import com.example.pqwsflowproject.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface Api {

    @FormUrlEncoded
    @POST("login")
    suspend fun Login( @Field("email") email:String , @Field("password") password:String): Response<LoginResponse>

    @GET("users")
    suspend fun getUsers():Response<List<User>>


    @GET("users")
    suspend fun getUser(@Url userId:String):Response<User>

    @FormUrlEncoded
    @POST("users")
    suspend fun creatUser(@Field("username") username:String,@Field("email") email:String , @Field("password") password:String,@Field("phone") phone:String,@Field("role") role:String,@Field("isActive") isActive:Boolean) :Response<CreatedUser>

    @GET("users")
    suspend fun getLocation():Response<List<LocationDetails>>

}