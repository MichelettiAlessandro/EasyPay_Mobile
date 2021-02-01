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
        /* "id": 3,
            "otp": "646825",
            "nome": "Ababua",
            "cognome": "Bau",
            "cf": "SNHFAIHCFIUHFHSYDCUHND",
            "phone": "+390123456789",
            "address": "Strada grande",
            "createdAt": "2021-01-13T19:02:55.402+0000",
            "updatedAt": "2021-01-13T19:02:55.402+0000",
            "ragSoc": "Pizzeria Mare Blu",
            "point": null,
            "piva": "SHKVIYNGARCNIYHCFAIHIANHAI",
            "birth_date": "1989-11-23T00:00:00.000+0000",
            "type": "commerciante",
            "id_conto": 3,
            "email": "user3@gmail.com"
        */
        if(token != ""){
            val userData  = profileViewModel.getUser(token)
            val nome = userData.getString("nome")
            val cognome = userData.getString("cognome")
            val cf = userData.getString("cf")
            var birth_date = userData.getString("birth_date")
            birth_date = birth_date.substring(0,10)

            val separated = birth_date.split("-".toRegex()).toTypedArray()
            var mm = separated[1]
            if(separated[1].length == 1){
                mm = "0"+separated[1];
            }
            val dd = separated[2]
            val yyyy = separated[0]
            val newDate = "$dd-$mm-$yyyy"

            val phone = userData.getString("phone")
            val address = userData.getString("address")
            val email = userData.getString("email")

            val fieldProfileNome = root.findViewById<TextView>(R.id.fieldProfileNome)
            fieldProfileNome.text = nome
            val fieldProfileCognome = root.findViewById<TextView>(R.id.fieldProfileCognome)
            fieldProfileCognome.text = cognome
            val fieldProfileCF = root.findViewById<TextView>(R.id.fieldProfileCF)
            fieldProfileCF.text = cf
            val fieldProfileDataNascita = root.findViewById<TextView>(R.id.fieldProfileDataNascita)
            fieldProfileDataNascita.text = newDate
            val fieldProfilePhone = root.findViewById<TextView>(R.id.fieldProfilePhone)
            fieldProfilePhone.text = phone
            val fieldProfileAddress = root.findViewById<TextView>(R.id.fieldProfileAddress)
            fieldProfileAddress.text = address
            val fieldProfileEmail = root.findViewById<TextView>(R.id.fieldProfileEmail)
            fieldProfileEmail.text = email
        }
        return root
    }
}
