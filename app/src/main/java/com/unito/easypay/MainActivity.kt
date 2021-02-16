package com.unito.easypay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    var token : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_payment, R.id.navigation_movimenti, R.id.navigation_profile))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.visibility = GONE
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.visibility = VISIBLE
        val navigation = intent.getStringExtra("navigation")?: "Default values if not provided"
        Log.d("navigation", navigation)
        if(navigation == "action_open_app_to_navigation_profile"){
            val token = intent.getStringExtra("token")?: ""
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("token", token)
                commit()
            }
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.action_open_app_to_navigation_profile)
        }
    }
}
