package com.unito.easypay.data

import android.os.StrictMode
import android.util.Log
import com.unito.easypay.data.model.LoggedInUser
import com.unito.easypay.ui.login.LoginActivity
import org.json.JSONObject
import java.io.IOException
import javax.net.ssl.HttpsURLConnection
import java.net.URL

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val urlstring = "https://easypay-unito.herokuapp.com/api/login"
            val token = getJSON(username, password, urlstring)
            val accessToken = token.getString("token")
            val User = LoggedInUser(java.util.UUID.randomUUID().toString(), accessToken)
            return Result.Success(User)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    @Throws(Throwable::class)
    private fun getJSON(username: String, password: String, urlString : String): JSONObject {
        var token = JSONObject()
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()

        val cred = JSONObject()
        cred.put("email", username)
        cred.put("password", password)
        StrictMode.setThreadPolicy(policy)
        val url = URL(urlString)
        val URLConnection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json;charset=UTF-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.write(cred.toString().toByteArray())
            outputStream.flush()
            outputStream.close()
            connect()
        }
        when (URLConnection.responseCode) {
            HttpsURLConnection.HTTP_OK -> {
                val bufferedReader = URLConnection.inputStream.bufferedReader()
                token = JSONObject(bufferedReader.use { it.readText() })
                bufferedReader.close()
            }
        }
        if(URLConnection.responseCode != HttpsURLConnection.HTTP_OK){
            throw Throwable("HTTP error code: " + URLConnection.responseCode)
        }
        URLConnection.disconnect()
        return token
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

