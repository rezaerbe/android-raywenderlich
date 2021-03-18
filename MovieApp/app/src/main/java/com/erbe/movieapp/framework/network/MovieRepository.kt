package com.erbe.movieapp.framework.network

import android.util.Log
import com.erbe.movieapp.framework.network.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService) {

    suspend fun fetchPopularMovies(): List<Movie>? {
        Log.d("TAG", "fetchPopularMovies")
        val deferredResponse = movieService.fetchPopularMoviesAsync().await()

        return if (deferredResponse.isSuccessful) {
            deferredResponse.body()?.movies
        } else {
            throw Exception()
        }
    }

    suspend fun fetchMovieByQuery(queryText: String): List<Movie>? {
        val deferredResponse = movieService.fetchMovieByQueryAsync(queryText).await()

        return if (deferredResponse.isSuccessful) {
            deferredResponse.body()?.movies
        } else {
            throw Exception()
        }
    }
}