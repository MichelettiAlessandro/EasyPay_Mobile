package com.unito.easypay.ui.profile

import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ProfileViewModel : ViewModel() {

    fun getUser(token : String): JSONObject {
        val urlstring = "https://easypay-unito.herokuapp.com/api/clienti/self"
        val userData = getJSON(urlstring, token)
        return userData
    }

    private fun getJSON(urlString: String, accessToken: String): JSONObject {
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        var result = JSONObject()
        var newtoken : String = accessToken
        val token = "" + newtoken
        StrictMode.setThreadPolicy(policy)
        val url = URL(urlString)
        val urlConnection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Authorization", "Bearer $token")
            readTimeout = 15000
            connectTimeout = 15000
            doOutput = false
            connect()
        }
        when (urlConnection.responseCode) {
            HttpsURLConnection.HTTP_OK -> {
                val bufferedReader = urlConnection.inputStream.bufferedReader()
                result = JSONObject(bufferedReader.use { it.readText() })
                Log.d("TEST", result.toString())
                bufferedReader.close()
            }
        }
        if(urlConnection.responseCode != HttpsURLConnection.HTTP_OK){
            throw Throwable("HTTP error code: " + urlConnection.responseCode)
        }
        urlConnection.disconnect()
        return result
    }
}