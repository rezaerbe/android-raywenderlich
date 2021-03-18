package com.erbe.tourx.ui.placelist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.tourx.R
import com.erbe.tourx.entities.Places
import com.erbe.tourx.ui.BaseActivity
import com.erbe.tourx.ui.details.PlaceDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class PlaceListActivity : BaseActivity<Places>() {

    private val viewModel: PlaceListViewModel by viewModels()

    private lateinit var adapter: PlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PlaceAdapter {
            PlaceDetailActivity.start(this, it)
        }
        recyclerView.adapter = adapter

        viewModel.loadOnReceive()
        viewModel.result.observe(this, this)
    }

    override fun onTaskLoading() {
        adapter.reset()
        progressBar.visibility = View.VISIBLE
    }

    override fun onDataLoaded(data: Places) {
        adapter.add(data)
    }

    override fun onTaskComplete() {
        progressBar.visibility = View.GONE
    }

    override fun onError(message: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@PlaceListActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLoadWhenReady -> viewModel.loadOnReceive()
            R.id.menuAtOnce -> viewModel.loadAllAtOnce()
            R.id.menuLoadQuickest -> viewModel.loadTheQuickestOne()
            R.id.enableExperimental -> viewModel.loadExperimental()
            R.id.switchOnNext -> viewModel.demonstrateSwitchOnNext()
            R.id.join -> viewModel.demonstrateJoinBehavior()
            R.id.dispose -> viewModel.disposeCurrentlyRunningStreams()
            R.id.groupJoin -> viewModel.demonstrateGroupJoin()
        }

        return super.onOptionsItemSelected(item)
    }
}