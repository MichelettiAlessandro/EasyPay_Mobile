package com.unito.easypay.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.unito.easypay.MainActivity
import com.unito.easypay.R
import com.unito.easypay.data.model.LoggedInUser

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val shared = activity?.getPreferences(Context.MODE_PRIVATE)
        var token = ""
        if (shared != null) {
            token = shared.getString("token", "dafValue").toString()
        }
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val image = root.findViewById<ImageView>(R.id.idQRcode)

        if (token != "") {
            var qrcode = homeViewModel.getQRCodeImage(token)
            image.setImageBitmap(qrcode)
        }

        return root
    }
}
