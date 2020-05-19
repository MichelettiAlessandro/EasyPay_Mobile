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

    fun login(username: String, password: String, loginActivity: LoginActivity): Result<LoggedInUser> {
        try {

            var urlstring = "https://easypay-unito.herokuapp.com/api/login"
            Log.d("TEST", "1")
            // val strCorsi = CoursesNetHelper.getJSON(MainActivity.applicationContext().resources.getString(R.string.restCourseList), 10000)
            val token = getJSON(username, password, 50000, urlstring);
            Log.d("TEST", "ciao")
            val User = LoggedInUser(java.util.UUID.randomUUID().toString(), token)
            return Result.Success(User)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    private fun getJSON(username: String, password: String, timeoutMillisecondi: Int, urlString : String): String {
        var token = ""
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()

        var cred = JSONObject()
        cred.put("username", username)
        cred.put("password", password)

        //Log.d("TEST", "1.1")
        StrictMode.setThreadPolicy(policy)
        val url = URL(urlString)
        //val jsonInputString : String  = "{ \"username\": \""+username+"\", \"password\": \""+password+"\"}";
        //Log.d("TEST", jsonInputString)
        //val input = jsonInputString.toByteArray(charset("utf-8"))
        val URLConnection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            setRequestProperty("Accept", "application/json");
            doOutput = true;
            outputStream.write(cred.toString().toByteArray())
            outputStream.flush()
            outputStream.close()
            connect()
        }
        when (URLConnection.responseCode) {
            HttpsURLConnection.HTTP_OK -> {
                val bufferedReader = URLConnection.inputStream.bufferedReader()

                Log.d("TEST", "2")
                token = bufferedReader.use { it.readText() }
                bufferedReader.close()
            }
        }
        if(URLConnection.responseCode != HttpsURLConnection.HTTP_OK){
            throw Throwable("HTTP error code: " + URLConnection.responseCode);
        }
        URLConnection.disconnect()

        return token
        Log.d("TEST", "3")

    }

    fun logout() {
        // TODO: revoke authentication
    }
}

