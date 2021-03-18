package com.erbe.foodeat.domain

import com.erbe.foodeat.database.RestaurantDao
import com.erbe.foodeat.domain.model.Restaurant
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class GetRestaurants(private val dataSource: RestaurantDao) {

    operator fun invoke(): Single<List<Restaurant>> {
        return dataSource.loadAllRestaurants()
            .delay(1000, TimeUnit.MILLISECONDS)
    }
}