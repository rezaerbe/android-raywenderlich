package com.erbe.movieapp.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.erbe.movieapp.R
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseUIElements()
    }

    private fun initialiseUIElements() {
        arguments?.let {
            movieNameTextView.text = MovieDetailFragmentArgs.fromBundle(it).movieName
        }
    }
}