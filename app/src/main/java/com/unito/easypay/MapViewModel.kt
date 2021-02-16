package com.unito.easypay

import MapRepository
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
        val mapRepository = MapRepository()
        return mapRepository.execute(token).get()
    }

}
