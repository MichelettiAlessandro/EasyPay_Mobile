package com.unito.easypay.ui.dashboard

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unito.easypay.R
import org.json.JSONObject

data class Movement(
    val data : String,
    var description : String,
    val importo : String
)

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var myListener: OnListFragmentInteractionListener? = null
    private lateinit var MovList : List<Array<String>>

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
            var userDataJSON = dashboardViewModel.getMovimenti(token)
            //Log.d("USER", userDataJSON.toString())
            // {"id":1,"budget":20,"saldo":0,"uscite":[],"entrate":[],"movimenti":[],"availableBudget":20,"id_cliente":1}
            var title = userDataJSON.getString("title")
            var descr = userDataJSON.getString("description")
            //var saldo = userDataJSON.getString("saldo")
            //var availableBudget = userDataJSON.getString("availableBudget")
            //var inMovements = userDataJSON.get("uscite")
            //var outMovements = userDataJSON.get("entrate")
            var movementsJSON  = userDataJSON.getJSONArray("movies")
            val listdata = ArrayList<Movement>()
            if (movementsJSON != null) {
                for (i in 0 until movementsJSON.length()) {
                    var x : JSONObject = movementsJSON[i] as JSONObject
                    var obj = Movement(x.get("id") as String, x.get("title") as String, x.get("releaseYear") as String)
                    listdata.add(obj)
                }
            }
            Log.d("MOVIMENTI", listdata.toString())

            //Add the following lines to create RecyclerView
            var rv : RecyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
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
