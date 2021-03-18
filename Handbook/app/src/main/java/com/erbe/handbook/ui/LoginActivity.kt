package com.erbe.handbook.ui

import android.os.Bundle
import android.widget.Toast
import com.erbe.handbook.R
import com.erbe.handbook.databinding.ActivityLoginBinding
import com.erbe.handbook.db.LoginState
import com.erbe.handbook.utils.startActivityAndClearStack

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{ loginHand() }
    }

    private fun loginHand() {
        val loginStatus = handsDb.performLogin(
            binding.usernameInput.text.toString(),
            binding.passwordInput.text.toString()
        )

        when (loginStatus) {
            LoginState.SUCCESS -> {
                //DONE: 9 Change the code below to use startActivityAndClearStack function
                startActivityAndClearStack(MainActivity::class.java, null)
            }
            LoginState.WRONG_PASSWORD -> {
                Toast.makeText(this, getString(R.string.wrong_password),
                    Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, getString(R.string.username_not_found),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}