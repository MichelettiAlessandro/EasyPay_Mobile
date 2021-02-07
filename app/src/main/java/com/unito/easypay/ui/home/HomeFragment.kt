package com.unito.easypay.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.unito.easypay.R
import com.unito.easypay.ui.profile.ProfileViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var profileViewModel = ProfileViewModel()

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
        val image = root.findViewById<ImageView>(R.id.idQRcode)
        val map = root.findViewById<Button>(R.id.buttonMap)

        map.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_payment_to_mapFragment)
        }

        val user = profileViewModel.getUser(token)
        if (user.length() != 0) {
            val otp = user.getString("otp")
            val id = user.getString("id_conto")
            val stringToEncode = "$id-$otp"
            var qrcode = homeViewModel.getQRCodeImage(stringToEncode)
            image.setImageBitmap(qrcode)
        }

        return root
    }
}
