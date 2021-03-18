package com.erbe.movieapp.di

import com.erbe.movieapp.framework.network.MovieApiModule
import com.erbe.movieapp.ui.MainActivity
import com.erbe.movieapp.ui.ViewModelModule
import com.erbe.movieapp.ui.movies.MovieListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MovieApiModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(movieListFragment: MovieListFragment)
}