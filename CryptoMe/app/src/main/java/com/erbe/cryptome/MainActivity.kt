package com.erbe.cryptome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.erbe.cryptome.adapters.FragmentAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupStatusBar()
        setupTabLayout()
    }

    private fun setupActionBar() {
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            Log.d("setupActionBar", e.toString())
        }
    }

    private fun setupTabLayout() {
        tabLayout = findViewById(R.id.tabLayout)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Western"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Eastern"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = FragmentAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager = findViewById(R.id.viewPager)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun setupStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }
}