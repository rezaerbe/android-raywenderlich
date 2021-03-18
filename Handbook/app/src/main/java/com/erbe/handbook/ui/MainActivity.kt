package com.erbe.handbook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.erbe.handbook.R
import com.erbe.handbook.databinding.ActivityMainBinding
import com.erbe.handbook.models.Hand
import com.erbe.handbook.utils.totalFingers

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    //DONE: 15 Change the Hand type to nullable
    private var currentHand: Hand? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DONE: 16 Call getLoggedInHand without non-null assertion
        currentHand = handsDb.getLoggedInHand()

        showDescription(currentHand)

        binding.logoutButton.setOnClickListener {
            handsDb.logoutHand()
            val mainIntent = Intent(this, SplashActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(mainIntent)
        }

        //DONE: 19 Call showGreeting on currentHand
        currentHand.showGreeting()
    }

    private fun showDescription(hand: Hand?) {
        binding.welcomeTv.text = getString(
                R.string.welcome_username,
                hand?.userName ?: "-")

        //DONE: 17 Change the code to use nullable Hand
        binding.userDescriptionTv.text = getString(R.string.user_description_total_fingers,
            hand?.bio ?: "-", hand?.totalFingers)
    }

    //DONE: 18 Add showGreeting method with scope of MainActivity
    private fun Hand?.showGreeting() {
        if (this == null) {
            Toast.makeText(this@MainActivity, getString(R.string.greeting_anonymous),
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, getString(R.string.greeting_user, userName),
                    Toast.LENGTH_SHORT).show()
        }
    }


}