package com.unito.easypay.ui.dashboard

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unito.easypay.R
import com.unito.easypay.ui.profile.ProfileViewModel
import org.json.JSONArray
import org.json.JSONObject

data class Movement(
    val data : String,
    var description : String,
    val importo : Double,
    val type : String
)

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var myListener: OnListFragmentInteractionListener? = null
    private lateinit var MovList : List<Array<String>>
    private var profileViewModel = ProfileViewModel()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val shared = activity?.getPreferences(Context.MODE_PRIVATE)
        var token = ""
        if (shared != null) {
            token = shared.getString("token", "dafValue").toString()
        }
        if(token != "") {
            var descriptionMov = ""
            var type = ""
            var importoRV = ""
            var importo : Double
            val user = profileViewModel.getUser(token)
            val id = user.getInt("id_conto")
            val userDataJSON = dashboardViewModel.getMovimenti(token, id)

            val movementsJSON  = userDataJSON.getJSONArray("movimenti")
            val budgetConto : Double = userDataJSON.get("budget") as Double
            val saldoConto : Double = userDataJSON.get("saldo") as Double

            view.findViewById<TextView>(R.id.saldoContoField).text = saldoConto.toString()
            view.findViewById<TextView>(R.id.budgetContoField).text = budgetConto.toString()

            val listdata = ArrayList<Movement>()
            for (i in 0 until movementsJSON.length()) {
                val x : JSONObject = movementsJSON[i] as JSONObject

                var data = x.get("timestamp") as String
                importo = x.get("valore") as Double
                data = data.substring(0,10)
                val separated = data.split("-".toRegex()).toTypedArray()
                var mm = separated[1]
                if(separated[1].length == 1){
                    mm = "0"+separated[1];
                }
                val dd = separated[2]
                val yyyy = separated[0]
                val dateMov = "$dd-$mm-$yyyy"
                if(x.get("type") == "pagamento"){
                    descriptionMov = x.get("to_name") as String
                    type = "P"
                }
                if(x.get("type") == "ricarica"){
                    type = "R"
                    if(x.get("from_name") is String){
                        descriptionMov = x.get("from_name") as String
                    }else{
                        descriptionMov = "Ricarica presso ATM"
                    }
                }

                val obj = Movement(dateMov,  descriptionMov , importo  , type )
                listdata.add(obj)
            }

            //Add the following lines to create RecyclerView
            val rv : RecyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(view.context)
            rv.adapter = MovementRecyclerViewAdapter(listdata)

            return view
        }
        return view
    }
    


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(courseCode: String)
    }
}
