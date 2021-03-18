package com.erbe.movieapp.framework.network

import com.erbe.movieapp.framework.network.model.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun fetchPopularMoviesAsync(): Deferred<Response<MoviesResponse>>

    @GET("search/movie")
    fun fetchMovieByQueryAsync(@Query("query") query: String): Deferred<Response<MoviesResponse>>
}