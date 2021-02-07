package com.unito.easypay

import android.os.Build
import android.os.StrictMode
import android.text.Html
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import java.io.BufferedReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MapViewModel : ViewModel() {

    fun getMap(token : String): String {
        return getHTML(token)
    }

    private fun getHTML(token: String): String {
        // i valori di latitudine e longitudine dovrebbero essere ricavati dalla posizione del cliente
        val lat = 45.0675487
        val lot = 7.6871531
        val urlString = "https://easypay-unito.herokuapp.com/map?lat=$lat&lon=$lot"
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        var result = ""
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
                result = urlConnection.inputStream.bufferedReader().use(BufferedReader::readText)
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
