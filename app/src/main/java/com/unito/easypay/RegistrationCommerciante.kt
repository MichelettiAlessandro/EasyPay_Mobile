package com.unito.easypay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationCommerciante.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationCommerciante : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var modelRegistrazione: ModelRegistrazione

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview: View = inflater.inflate(R.layout.fragment_registration_commerciante, container, false)
        val registrazione: Button = rootview.findViewById(R.id.registrazioneComm)
        registrazione.setOnClickListener {

            /*
            {
                "id": 3,
                "otp": "646825",
                "nome": "Ababua",
                "cognome": "Bau",
                "cf": "SNHFAIHCFIUHFHSYDCUHND",
                "phone": "+390123456789",
                "address": "Strada grande",
                "ragSoc": "Pizzeria Mare Blu",
                "piva": "SHKVIYNGARCNIYHCFAIHIANHAI",
                "birth_date": "1989-11-23T00:00:00.000+0000",
                "type": "commerciante",
                "email": "user3@gmail.com",

            }
             */
            val fieldNome = rootview.findViewById<TextView>(R.id.fieldProfileNome).text.toString();
            val fieldCognome = rootview.findViewById<TextView>(R.id.fieldProfileCognome).text.toString();
            val fieldCodiceFiscale = rootview.findViewById<TextView>(R.id.fieldCodiceFiscale).text.toString();
            val fieldEmail = rootview.findViewById<TextView>(R.id.fieldEmail).text.toString();
            val fieldPassword = rootview.findViewById<TextView>(R.id.fieldPassword).text.toString();
            val data = JSONObject()

            data.put("nome", fieldNome)
            data.put("cognome", fieldCognome)
            data.put("cf", fieldCodiceFiscale)
            data.put("type", "cliente")
            //data.put("telefono", R.id.fieldTelefono.toString())
            data.put("email", fieldEmail)
            data.put("password", fieldPassword)
            //data.put("confermapassword", R.id.fieldConfermaPassword.toString())
            modelRegistrazione = ViewModelProvider(this).get(ModelRegistrazione::class.java)
            var result = modelRegistrazione.registration(data)
            //var result = model.registrazione(data)


            // Mi sposto nell'interfaccia dei movimenti
            findNavController().navigate(R.id.action_registrationCliente_to_open_app)
        }
        return rootview
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistrationCommerciante.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationCommerciante().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
