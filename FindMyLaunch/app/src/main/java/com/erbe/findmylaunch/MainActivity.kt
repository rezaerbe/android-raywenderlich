package com.erbe.findmylaunch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.erbe.findmylaunch.databinding.ActivityMainBinding
import com.erbe.findmylaunch.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FindMyLaunch)
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
//                setCustomAnimations(
//                    R.anim.fragment_fade_enter,
//                    R.anim.fragment_fade_exit,
//                    R.anim.fragment_fade_enter,
//                    R.anim.fragment_fade_exit
//                )
                add(R.id.fragmentContainer, SearchFragment())
            }
        }
    }
}