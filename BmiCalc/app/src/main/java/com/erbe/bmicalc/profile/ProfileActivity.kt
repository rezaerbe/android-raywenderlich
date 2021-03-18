package com.erbe.bmicalc.profile

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erbe.bmicalc.BMIApplication
import com.erbe.bmicalc.R
import com.erbe.bmicalc.databinding.ActivityProfileBinding
import com.erbe.bmicalc.model.Person
import com.erbe.bmicalc.profile.viewmodel.ProfileViewModel
import com.erbe.bmicalc.profile.viewmodel.ProfileViewModelFactory
import com.erbe.bmicalc.util.showDatePickerDialog
import com.erbe.bmicalc.util.toFormattedString

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ProfileViewModelFactory((application as BMIApplication).repository)
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextBirthdate.setOnClickListener {
            showDatePickerDialog(binding.editTextBirthdate)
        }

        viewModel.loadProfile()
        viewModel.person.observe(this, Observer(this::showProfile))
        viewModel.isSaved.observe(this, Observer(this::showSaved))
    }

    private fun showProfile(person: Person?) {
        person?.let {
            binding.editTextBirthdate.setText(it.birthdate.toFormattedString())
            binding.editTextHeight.setText(it.height.toFormattedString())
        }

        binding.fab.setOnClickListener {
            viewModel.saveProfile(binding.editTextBirthdate.text.toString(), binding.editTextHeight.text.toString())
        }
    }

    private fun showSaved(success: Boolean) {
        if (success) {
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.profile_invalid_inputs), Toast.LENGTH_LONG).show()
        }
    }
}