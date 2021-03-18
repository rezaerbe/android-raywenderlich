package com.erbe.customersurvey.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.erbe.customersurvey.R

class HomeActivity : AppCompatActivity() {

    private val appBarConfiguration = AppBarConfiguration(setOf(R.id.allSurveysFragment, R.id.startSurveyFragment))

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CustomerSurvey)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.navHostFragment).navigateUp() || super.onNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Navigation.findNavController(this, R.id.navHostFragment).navigateUp(appBarConfiguration)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}