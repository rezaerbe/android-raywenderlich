package com.erbe.foodeat.injection

import com.erbe.foodeat.database.RestaurantDatabase
import com.erbe.foodeat.domain.GetRestaurants
import com.erbe.foodeat.domain.SaveRestaurants
import com.erbe.foodeat.presentation.RestaurantViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restaurantModule = module {

    single { RestaurantDatabase.getInstance(androidContext()).restaurantDao() }

    single { GetRestaurants(get()) }

    single { SaveRestaurants(get()) }

    viewModel {
        RestaurantViewModel(getRestaurants = get(),
            saveRestaurants = get())
    }
}