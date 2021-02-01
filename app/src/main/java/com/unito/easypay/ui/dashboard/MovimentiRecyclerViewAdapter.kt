package com.unito.easypay.ui.dashboard

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unito.easypay.R


class MovementRecyclerViewAdapter(val data: ArrayList<Movement>) :
    RecyclerView.Adapter<MovementRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_dashboard_element, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val u = data[position] //access data item
        holder.bind(u);
    }

    inner class ViewHolder(myView: View) : RecyclerView.ViewHolder(myView) {
        private val dataobj : TextView = myView.findViewById(R.id.data)
        private val descr : TextView = myView.findViewById(R.id.description)
        private val importo : TextView = myView.findViewById(R.id.importo)
        fun bind(u : Movement){
            dataobj.text = u.data
            descr.text = u.description
            importo.text = u.importo.toString()
            if(u.type == "P"){
                importo.setTextColor(Color.rgb(205,55,55))
            }
            if(u.type == "R"){
                importo.setTextColor(Color.rgb(58,172,63))
            }
        }
    }
}
