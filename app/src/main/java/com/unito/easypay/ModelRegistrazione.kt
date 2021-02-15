package com.unito.easypay

import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.ViewModel
import com.unito.easypay.data.Result
import com.unito.easypay.data.model.LoggedInUser
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class ModelRegistrazione : ViewModel() {

    fun registration(data: JSONObject): String {
        val url = "https://easypay-unito.herokuapp.com/api/clienti"
        val data = getJSON(data, url)
        val idconto = data.getString("id_conto")
        return idconto
    }

    @Throws(Throwable::class)
    private fun getJSON(data : JSONObject, urlString : String): JSONObject {
        var token = JSONObject()
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        Log.d("REG", data.toString())
        StrictMode.setThreadPolicy(policy)
        val url = URL(urlString)
        val urlConnection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json;charset=UTF-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.write(data.toString().toByteArray())
            outputStream.flush()
            outputStream.close()
            connect()
        }
        when (urlConnection.responseCode) {
            HttpsURLConnection.HTTP_OK -> {
                val bufferedReader = urlConnection.inputStream.bufferedReader()

                Log.d("REG", "3")
                token = JSONObject(bufferedReader.use { it.readText() })
                Log.d("REG", token.toString())
                bufferedReader.close()
            }
        }
        if(urlConnection.responseCode != HttpsURLConnection.HTTP_OK){
            throw Throwable("HTTP error code: " + urlConnection.responseCode)
        }
        urlConnection.disconnect()
        return token
    }

}