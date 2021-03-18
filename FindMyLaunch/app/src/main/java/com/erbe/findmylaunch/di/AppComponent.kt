package com.erbe.findmylaunch.di

import android.content.Context
import com.erbe.findmylaunch.details.DetailsViewModel
import com.erbe.findmylaunch.search.SearchViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class])
@Singleton
interface AppComponent {

    fun searchViewModelFactory(): SearchViewModel.Factory
    fun detailsViewModelFactory(): DetailsViewModel.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }
}