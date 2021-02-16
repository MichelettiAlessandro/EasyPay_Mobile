import android.os.AsyncTask
import android.os.StrictMode
import com.unito.easypay.ui.dashboard.Params
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class DashboardRepository : AsyncTask<Params, Void?, JSONObject>() {

        override fun doInBackground(vararg params: Params): JSONObject {
            val token: String = params[0].token
            val idconto: Int = params[0].idconto
            val urlString = "https://easypay-unito.herokuapp.com/api/movimenti?conto=$idconto"
            val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
            var result = JSONObject()
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

        override fun onPostExecute(result: JSONObject) {
            super.onPostExecute(result)
        }
    }