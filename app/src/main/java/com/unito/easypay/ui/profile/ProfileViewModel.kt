package com.unito.easypay.ui.profile

import ProfileRepository
import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unito.easypay.R
import com.unito.easypay.data.Result
import com.unito.easypay.ui.login.LoggedInUserView
import com.unito.easypay.ui.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ProfileViewModel : ViewModel() {

    fun getUser(token : String): JSONObject {
        val urlstring = "https://easypay-unito.herokuapp.com/api/clienti/self"
        //val userData = getJSON(urlstring, token)
        val profileRepository = ProfileRepository()
        val userdata = profileRepository.execute(token).get()
        return userdata
    }
    /*
    private fun getJSON(urlString: String, accessToken: String): JSONObject {
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        var result = JSONObject()
        val newtoken : String = accessToken
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
                bufferedReader.close()
            }
        }
        if(urlConnection.responseCode != HttpsURLConnection.HTTP_OK){
            throw Throwable("HTTP error code: " + urlConnection.responseCode)
        }
        urlConnection.disconnect()
        return result
    }
     */
}