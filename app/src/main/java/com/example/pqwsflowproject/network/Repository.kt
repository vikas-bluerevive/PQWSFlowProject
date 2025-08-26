package com.example.pqwsflowproject.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object Repository {
    var firstlogin = false
    val BASE_URL ="https://tanktotankbackend.onrender.com/api/v1/"
    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

   /* val httpClient: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().addHeader(
                    "Authorization",
                    "Bearer "
                ).build()
            )
        }
        .build()*/


    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(Api::class.java)

}