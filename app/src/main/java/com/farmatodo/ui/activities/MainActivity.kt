package com.farmatodo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.farmatodo.R
import com.farmatodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        setupNavController()
        updateToolbar(Navigation.findNavController(this, R.id.nav_host_fragment).currentDestination)
    }

    private fun setupNavController(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateToolbar(destination)
        }
    }

    private fun updateToolbar(destination: NavDestination?) {
        val haveIconBackDisplayHome = destination?.id != R.id.homeFragment
        supportActionBar?.title = destination?.label
        supportActionBar?.setDisplayHomeAsUpEnabled(haveIconBackDisplayHome)
        supportActionBar?.setDisplayShowHomeEnabled(haveIconBackDisplayHome)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
