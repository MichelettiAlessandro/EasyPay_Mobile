package com.unito.easypay.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.unito.easypay.R

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val shared = activity?.getPreferences(Context.MODE_PRIVATE)
        var token = ""
        if (shared != null) {
            token = shared.getString("token", "dafValue").toString()
        }
        if(token != ""){
            var userData  = profileViewModel.getUser(token)
            var nome = userData.getString("nome")
            var cognome = userData.getString("cognome")
            var cf = userData.getString("cf")

            val fieldNome = root.findViewById<TextView>(R.id.fieldNome)
            fieldNome.text = nome
            val fieldCognome = root.findViewById<TextView>(R.id.fieldCognome)
            fieldCognome.text = cognome
            val fieldCF = root.findViewById<TextView>(R.id.fieldCF)
            fieldCF.text = cf

        }


        return root
    }
}
