package com.erbe.sunzoid.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearSnapHelper
import com.erbe.sunzoid.R
import com.erbe.sunzoid.ui.home.mapper.LocationViewState
import com.erbe.sunzoid.util.image_loader.ImageLoader
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val imageLoader: ImageLoader by inject()
    private val homeViewModel by viewModel<HomeViewModel>()

    private val forecastAdapter by lazy { ForecastAdapter(layoutInflater, imageLoader) }
    private val locationAdapter by lazy { LocationAdapter(layoutInflater, ::onLocationClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUi()
    }

    private fun initUi() {
        initSearchBar()
        initRecyclerView()
        initObservers()
    }

    private fun initSearchBar() {
        locationsList.adapter = locationAdapter

        search.isActivated = true
        search.onActionViewExpanded()
        search.isIconified = true
        search.clearFocus()

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                homeViewModel.queryChannel.offer(newText)
                return false
            }
        })
    }

    private fun initRecyclerView() {
        forecastList.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        forecastList.adapter = forecastAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(forecastList)
    }

    private fun initObservers() {
        homeViewModel.locations.observe(this, Observer {
            locationAdapter.setData(it)
        })

        homeViewModel.forecasts.observe(this, Observer {
            forecastAdapter.setData(it)
        })
    }

    private fun onLocationClick(locationViewState: LocationViewState) {
        homeViewModel.queryChannel.offer("")
        homeViewModel.fetchLocationDetails(locationViewState.id)
    }
}