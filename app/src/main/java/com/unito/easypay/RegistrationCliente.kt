package com.unito.easypay

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        var rootview: View = inflater.inflate(
            R.layout.fragment_registration_cliente,
            container,
            false
        )
        var registrazione: Button = rootview.findViewById(R.id.registrazioneCli)

        registrazione.setOnClickListener {

            var fieldNome = rootview.findViewById<TextView>(R.id.fieldNome).text.toString();
            var fieldCognome = rootview.findViewById<TextView>(R.id.fieldCognome).text.toString();
            var fieldCodiceFiscale = rootview.findViewById<TextView>(R.id.fieldCodiceFiscale).text.toString();
            var fieldEmail = rootview.findViewById<TextView>(R.id.fieldEmail).text.toString();
            var fieldPhone = rootview.findViewById<TextView>(R.id.fieldTelefono).text.toString();
            var fieldAddress = rootview.findViewById<TextView>(R.id.fieldAddress).text.toString();
            var fieldBirthDate = rootview.findViewById<TextView>(R.id.fieldDataNascita).text.toString();
            var fieldPassword = rootview.findViewById<TextView>(R.id.fieldPassword).text.toString();
            var data = JSONObject()

            val separated = fieldBirthDate.split("/".toRegex()).toTypedArray()
            var mm = separated[1]
            if(separated[1].length == 1){
                mm = "0"+separated[1];
            }
            val yyyy = separated[2]
            val dd = separated[0]
            val newDate = "$yyyy-$mm-$dd"

            if(fieldNome != ""
                && fieldCognome != ""
                && fieldCodiceFiscale != ""
                && fieldEmail != ""
                && fieldPhone != ""
                && fieldAddress != ""
                && fieldBirthDate != ""
                && fieldPassword != ""){

                data.put("nome", fieldNome)
                data.put("cognome", fieldCognome)
                data.put("cf", fieldCodiceFiscale)
                data.put("type", "cliente")
                data.put("phone", fieldPhone)
                data.put("email", fieldEmail)
                data.put("password", fieldPassword)
                data.put("birth_date", newDate)
                data.put("address", fieldAddress)

                Log.d("REG", data.toString())

                modelRegistrazione = ViewModelProvider(this).get(ModelRegistrazione::class.java)
                var result = modelRegistrazione.registration(data)
                Log.d("REG", result.toString())

                findNavController().navigate(R.id.action_registrationCliente_to_open_app)
            }else{
                showAlertDialogButtonClicked(rootview)
            }
        }
        return rootview
    }
    private fun showAlertDialogButtonClicked(view: View?) {        // setup the alert builder
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("My title")
        builder.setMessage("This is my message.") // add a button
        builder.setPositiveButton("OK", null) // create and show the alert dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
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
