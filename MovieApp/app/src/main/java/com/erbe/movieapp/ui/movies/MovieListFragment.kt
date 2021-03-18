package com.erbe.movieapp.ui.movies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.erbe.movieapp.MovieApplication.Companion.application
import com.erbe.movieapp.R
import com.erbe.movieapp.connectivity.ConnectivityLiveData
import com.erbe.movieapp.framework.network.model.Movie
import com.erbe.movieapp.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieAdapter.MovieClickListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var connectivityLiveData: ConnectivityLiveData

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            mainViewModel.onSearchQuery(editable.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityLiveData = ConnectivityLiveData(application)

        application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseObservers()
        initialiseUIElements()
    }

    private fun initialiseObservers() {
        mainViewModel.moviesMediatorData.observe(viewLifecycleOwner, Observer {
            movieAdapter.updateData(it)
        })

        mainViewModel.movieLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onMovieLoadingStateChanged(it)
        })

        connectivityLiveData.observe(viewLifecycleOwner, Observer { isAvailable ->
            when (isAvailable) {
                true -> {
                    mainViewModel.onFragmentReady()
                    statusButton.visibility = View.GONE
                    moviesRecyclerView.visibility = View.VISIBLE
                    searchEditText.visibility = View.VISIBLE
                }
                false -> {
                    statusButton.visibility = View.VISIBLE
                    moviesRecyclerView.visibility = View.GONE
                    searchEditText.visibility = View.GONE
                }
            }
        })

        mainViewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { movieTitle ->
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieClicked(movieTitle)
                )
            }
        })
    }

    private fun initialiseUIElements() {
        searchEditText.addTextChangedListener(searchTextWatcher)
        movieAdapter = MovieAdapter(this)
        moviesRecyclerView.apply {
            adapter = movieAdapter
            hasFixedSize()
        }
    }

    override fun onMovieClicked(movie: Movie) {
        mainViewModel.onMovieClicked(movie)
    }

    private fun onMovieLoadingStateChanged(state: MovieLoadingState) {
        when (state) {
            MovieLoadingState.LOADING -> {
                statusButton.visibility = View.GONE
                moviesRecyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.VISIBLE
            }
            MovieLoadingState.LOADED -> {
                connectivityLiveData.value?.let {
                    if (it) {
                        statusButton.visibility = View.GONE
                        moviesRecyclerView.visibility = View.VISIBLE
                    } else {
                        statusButton.visibility = View.VISIBLE
                        moviesRecyclerView.visibility = View.GONE
                    }
                }
                loadingProgressBar.visibility = View.GONE
            }
            MovieLoadingState.ERROR -> {
                statusButton.visibility = View.VISIBLE
                context?.let {
                    statusButton.setCompoundDrawables(
                        null, ContextCompat.getDrawable(it, R.drawable.no_internet), null,
                        null)
                }
                moviesRecyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            }
            MovieLoadingState.INVALID_API_KEY -> {
                statusButton.visibility = View.VISIBLE
                statusButton.text = getString(R.string.invalid_api_key)
                statusButton.setCompoundDrawables(null, null, null, null)
                moviesRecyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            }
        }
    }

}