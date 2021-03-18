package com.erbe.handbook.ui

import android.os.Bundle
import android.widget.Toast
import com.erbe.handbook.R
import com.erbe.handbook.databinding.ActivityRegisterBinding
import com.erbe.handbook.db.RegistrationState
import com.erbe.handbook.models.Hand
import com.erbe.handbook.utils.startActivityAndClearStack
import com.erbe.handbook.utils.validateUsername

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener { registerHand() }
    }

    private fun registerHand() {
        val username = binding.usernameInput.text.toString()
        val fingersCount = binding.fingersCountInput.text.toString()
        val thumbsCount = binding.thumbCountInput.text.toString()
        val bio = binding.bioInput.text.toString()
        val password = binding.passwordInput.text.toString()

        if (username.isEmpty() || fingersCount.isEmpty() || thumbsCount.isEmpty() ||
                bio.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.mandatory_fields), Toast.LENGTH_SHORT).show()
            return
        }

        //DONE: 12 Add validation for username
        val isUsernameValid = binding.usernameInput.validateUsername()
        if (!isUsernameValid) {
            return
        }

        val hand = Hand(username, fingersCount.toInt(), thumbsCount.toInt(), bio, password)
        val registrationResult: RegistrationState = handsDb.registerHand(hand)

        if (registrationResult == RegistrationState.SUCCESS) {
            //DONE: 10 Change the code below to use startActivityAndClearStack function
            startActivityAndClearStack(MainActivity::class.java, null)
        } else {
            Toast.makeText(this, getString(R.string.username_already_exists),
                Toast.LENGTH_SHORT).show()
        }
    }
}