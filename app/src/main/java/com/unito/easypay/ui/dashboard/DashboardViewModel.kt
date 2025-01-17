package com.unito.easypay.ui.dashboard

import DashboardRepository
import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

data class Params(
        val token : String,
        var idconto : Int,
)
class DashboardViewModel : ViewModel() {


    fun getMovements(token : String, idconto : Int): JSONObject {

        val params = Params(token, idconto)
        val mapRepository = DashboardRepository()
        return mapRepository.execute(params).get()
    }

    /*
    {
    "id": 1,
    "budget": 20.0,
    "saldo": 0.0,
    "uscite": [],
    "entrate": [],
    "availableBudget": 20.0,
    "movimenti": [],
    "id_cliente": 1
    }
    */
    fun getMovimenti(accessToken: String, idconto : Int): JSONObject {
        val urlstring = "https://easypay-unito.herokuapp.com/api/movimenti?conto=$idconto"
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        var result = JSONObject()
        //var newtoken : String = accessToken
        //val token = "" + newtoken
        StrictMode.setThreadPolicy(policy)
        val url = URL(urlstring)
        val urlConnection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Authorization", "Bearer $accessToken")
            readTimeout = 15000
            connectTimeout = 15000
            doOutput = false
            connect()
        }
        when (urlConnection.responseCode) {
            HttpsURLConnection.HTTP_OK -> {
                val bufferedReader = urlConnection.inputStream.bufferedReader()
                result = JSONObject(bufferedReader.use { it.readText() })
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