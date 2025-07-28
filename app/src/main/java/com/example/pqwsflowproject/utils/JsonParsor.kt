package com.example.pqwsflowproject.utils

import android.util.Log
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient


import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException


object JsonParsor {

    var `is`: InputStream? = null
    var jObj: JSONObject? = null
    var json = ""

    // constructor
    fun JSONParser() {}

    fun getJSONFromUrl(url: String?): String? {

        // Making HTTP request
        try {
            // defaultHttpClient
            val httpClient = DefaultHttpClient()
            val httpPost = HttpPost(url)
            val httpResponse: HttpResponse = httpClient.execute(httpPost)
            val httpEntity: HttpEntity = httpResponse.getEntity()
            `is` = httpEntity.getContent()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: ClientProtocolException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            val reader = BufferedReader(
                InputStreamReader(`is`, "iso-8859-1"), 8
            )
            val sb = StringBuilder()
            var line: String? = null
            while (reader.readLine().also { line = it } != null) {
                sb.append(
                    """
                    $line
                    
                    """.trimIndent()
                )
            }
            json = sb.toString()
            `is`!!.close()
        } catch (e: Exception) {
            Log.e("Buffer Error", "Error converting result $e")
        }
        return json
    }
}