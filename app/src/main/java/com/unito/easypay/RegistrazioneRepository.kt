package com.unito.easypay

import android.os.AsyncTask
import android.os.StrictMode
import android.util.Log
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class RegistrazioneRepository : AsyncTask<JSONObject, Void?, JSONObject>() {

        override fun doInBackground(vararg params: JSONObject): JSONObject {
            val data: JSONObject = params[0]
            var token = JSONObject()
            val urlString = "https://easypay-unito.herokuapp.com/api/clienti"
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

        override fun onPostExecute(result: JSONObject) {
            super.onPostExecute(result)
        }
    }