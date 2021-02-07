package com.unito.easypay.ui.profile

import android.os.StrictMode
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LogoutViewModel {

    fun logout(token: String) : Boolean {
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        var isok : Boolean = false
        val urlString = "https://easypay-unito.herokuapp.com/api/logout"
        StrictMode.setThreadPolicy(policy)
        val url = URL(urlString)
        val urlConnection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Authorization", "Bearer $token")
            readTimeout = 15000
            connectTimeout = 15000
            doOutput = false
            connect()
        }
        when (urlConnection.responseCode) {
            HttpsURLConnection.HTTP_OK -> {
                isok = true
            }
        }
        if(urlConnection.responseCode != HttpsURLConnection.HTTP_OK){
            throw Throwable("HTTP error code: " + urlConnection.responseCode)
        }
        urlConnection.disconnect()
        return isok
    }
}
