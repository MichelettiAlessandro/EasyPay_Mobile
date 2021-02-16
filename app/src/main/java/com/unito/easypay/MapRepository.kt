import android.os.AsyncTask
import android.os.StrictMode
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MapRepository : AsyncTask<String, Void?, String>() {

        override fun doInBackground(vararg params: String): String {
            val token: String = params[0]
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

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
        }
    }