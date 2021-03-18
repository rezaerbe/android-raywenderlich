package com.erbe.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.erbe.movieapp.MovieApplication
import com.erbe.movieapp.R
import com.erbe.movieapp.ui.about.AboutDialogActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MovieApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MovieApplication.application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutMenuItem -> {
                startActivity(Intent(this, AboutDialogActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}