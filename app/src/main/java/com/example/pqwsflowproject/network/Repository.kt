package com.example.pqwsflowproject.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object Repository {
    val BASE_URL ="https://987c7c36379f.ngrok-free.app/api/v1/"
    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logging)
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