import android.os.AsyncTask
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class ProfileRepository : AsyncTask<String, Void?, JSONObject>() {

        override fun doInBackground(vararg params: String): JSONObject {
            val token: String = params[0]
            var result = JSONObject()
            val stringUrl = "https://easypay-unito.herokuapp.com/api/clienti/self"
            //Create a URL object holding our url
            val url : URL  = URL(stringUrl);         //Create a connection
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
                return result
            }
            urlConnection.disconnect()
            return result
        }

        override fun onPostExecute(result: JSONObject) {
            super.onPostExecute(result)
        }
    }