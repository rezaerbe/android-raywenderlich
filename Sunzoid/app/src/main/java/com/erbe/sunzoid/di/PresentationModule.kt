package com.erbe.sunzoid.di

import com.erbe.sunzoid.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val presentationModule = module {
    viewModel { HomeViewModel(get(), get()) }
}