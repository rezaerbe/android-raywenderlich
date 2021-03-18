package com.erbe.handbook.ui

import android.content.Intent
import android.os.Bundle
import com.erbe.handbook.R
import com.erbe.handbook.databinding.ActivityOnboardingBinding
import com.erbe.handbook.utils.loadImage

class OnBoardingActivity : BaseActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DONE: 2 Replace the below code with extension function from Extensions.kt file
        binding.imageIcon.loadImage(getString(R.string.logo_url))

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //DONE: 6 Call showGreetingMessage
        showGreetingMessage(this)
    }

    //DONE: 5 Add method below to use greeting extension function
    private fun showGreetingMessage(activity: BaseActivity) {
        activity.greet()
    }
}