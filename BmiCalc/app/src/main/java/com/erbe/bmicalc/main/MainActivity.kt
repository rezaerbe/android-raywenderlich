package com.erbe.bmicalc.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erbe.bmicalc.BMIApplication
import com.erbe.bmicalc.R
import com.erbe.bmicalc.databinding.ActivityMainBinding
import com.erbe.bmicalc.log.LogActivity
import com.erbe.bmicalc.main.model.MainUIModel
import com.erbe.bmicalc.main.viewmodel.MainViewModel
import com.erbe.bmicalc.main.viewmodel.MainViewModelFactory
import com.erbe.bmicalc.model.Person
import com.erbe.bmicalc.profile.ProfileActivity

private const val ADD_LOG_REQUEST = 123
private const val PROFILE_REQUEST = 456

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BmiCalc)
        super.onCreate(savedInstanceState)

        val factory = MainViewModelFactory((application as BMIApplication).repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.logs.observe(this, Observer(this::showLogs))

        viewModel.loadLogs()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.profile) {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivityForResult(intent, PROFILE_REQUEST)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showLogs(uiModel: MainUIModel) {
        when (uiModel) {
            is MainUIModel.ExistingProfile -> showExistingProfile(uiModel.person)
            is MainUIModel.MissingProfile -> showMissingProfile()
        }
    }

    private fun showMissingProfile() {
        binding.fab.visibility = View.GONE
        binding.textViewMissingProfile.visibility = View.VISIBLE
    }

    private fun showExistingProfile(person: Person) {
        binding.recyclerView.adapter = PersonAdapter(person)
        binding.recyclerView.scrollToPosition(0)
        binding.fab.setOnClickListener {
            val intent = LogActivity.newIntent(this, person)
            startActivityForResult(intent, ADD_LOG_REQUEST)
        }
        binding.fab.visibility = View.VISIBLE
        binding.textViewMissingProfile.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_LOG_REQUEST || requestCode == PROFILE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadLogs()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}