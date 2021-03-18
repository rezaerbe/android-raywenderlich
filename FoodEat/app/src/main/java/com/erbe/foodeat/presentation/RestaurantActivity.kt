package com.erbe.foodeat.presentation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.erbe.foodeat.R
import com.erbe.foodeat.domain.model.Restaurant
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_restaurants.*
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantActivity : AppCompatActivity() {

    private val viewModel: RestaurantViewModel by viewModel()
    private val restaurantListAdapter = RestaurantListAdapter()

    private val querySubject = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FoodEat)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)

        restaurantsList.adapter = restaurantListAdapter

        viewModel.restaurantsLiveData.observe(this, Observer {
            handleDataChange(it)
        })

        viewModel.uiLiveData.observe(this, Observer {
            handleUIChange(it)
        })

        viewModel.setQueryListener(querySubject)
    }

    private fun handleUIChange(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Success -> Toast.makeText(this, getString(R.string.results_fetched), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleDataChange(resource: Resource<List<Restaurant>>) {
        hideLoading()
        when (resource) {
            is Resource.Success -> restaurantListAdapter.updateRestaurants(resource.data)
            Resource.Loading -> showLoading()
            is Resource.Error -> showError(resource.message)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String): Boolean {
                querySubject.onNext(newText)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_all -> {
                viewModel.getAllRestaurants()
            }
            R.id.action_top -> {
                viewModel.getTopRestaurants()
            }
            R.id.action_bottom -> {
                viewModel.getLowestRatedRestaurants()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading() {
        loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingIndicator.visibility = View.GONE
    }

    private fun showError(message: String?) {
        Log.e(this.javaClass.simpleName, message ?: "Something went wrong!")
    }
}