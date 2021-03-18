package com.erbe.tennisplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erbe.tennisplayer.database.PlayerListItem
import com.erbe.tennisplayer.details.DetailsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var adapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.Theme_TennisPlayer)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this@MainActivity, RecyclerView
            .VERTICAL)
        )
        adapter = PlayerAdapter(mutableListOf())
        recyclerView.adapter = adapter
        adapter.setOnPlayerTapListener { player ->
            val fragment = DetailsFragment.newInstance(player)

            fragment.show(supportFragmentManager, "DetailsFragment")
        }

        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        playerViewModel.getAllPlayers().observe(this, Observer<List<PlayerListItem>> { players ->
            adapter.swapData(players)
        })
    }
}