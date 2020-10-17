package com.unito.easypay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationCliente.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationCliente : Fragment() {
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
        var rootview: View = inflater.inflate(R.layout.fragment_registration_cliente, container, false)
        var registrazione: Button = rootview.findViewById(R.id.registrazioneCli)
        registrazione.setOnClickListener {

                var data = JSONObject()
                data.put("nome", R.id.fieldNome.toString())
                data.put("cognome", R.id.fieldCognome.toString())
                data.put("cf", R.id.fieldCodiceFiscale.toString())
                data.put("type", "cliente")
                //data.put("telefono", R.id.fieldTelefono.toString())
                data.put("username", R.id.fieldEmail.toString())
                data.put("password", R.id.fieldPassword.toString())
                //data.put("confermapassword", R.id.fieldConfermaPassword.toString())
                Log.d("REG", data.toString())
                modelRegistrazione = ViewModelProvider(this).get(ModelRegistrazione::class.java)
                var result = modelRegistrazione.registration(data)
                Log.d("REG", result.toString())
            //var result = model.registrazione(data)


            // Mi sposto nell'interfaccia dei movimenti
            // findNavController().navigate(R.id.)
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
         * @return A new instance of fragment RegistrationCliente.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationCliente().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
