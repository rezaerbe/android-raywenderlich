package com.erbe.foodeat.domain

import com.erbe.foodeat.database.RestaurantDao
import com.erbe.foodeat.domain.model.Restaurant
import io.reactivex.Completable

class SaveRestaurants(private val dataSource: RestaurantDao) {

    operator fun invoke(): Completable {
        return dataSource.insertRestaurants(defaultRestaurants)
    }

    private val defaultRestaurants = listOf(
        Restaurant(name = "Island Grill", rating = 4),
        Restaurant(name = "Flavoroso", rating = 5),
        Restaurant(name = "Green Curry", rating = 1),
        Restaurant(name = "El Pirata Porch", rating = 2),
        Restaurant(name = "Sweet Escape", rating = 2),
        Restaurant(name = "Maute Steak", rating = 3),
        Restaurant(name = "Salty Squid", rating = 4),
        Restaurant(name = "Bangalore Spices", rating = 3),
        Restaurant(name = "Pancake World", rating = 2),
        Restaurant(name = "Veganic Corner", rating = 2),
        Restaurant(name = "Masala", rating = 1),
        Restaurant(name = "Grassfed Grill", rating = 1),
        Restaurant(name = "Inside Burger", rating = 3),
        Restaurant(name = "Greenanic Smoothies", rating = 4),
        Restaurant(name = "Freddy's Stove", rating = 5),
        Restaurant(name = "Grandma's Sweets", rating = 2),
        Restaurant(name = "Spicella Spanish Kitchen", rating = 2),
        Restaurant(name = "Xin Chao Vietnamese", rating = 1),
        Restaurant(name = "Paterro's Kitchen", rating = 5),
        Restaurant(name = "Fishly", rating = 3),
        Restaurant(name = "Mediterra Seafood", rating = 4),
        Restaurant(name = "Street Deli", rating = 5),
        Restaurant(name = "Whispering Bamboo", rating = 1),
        Restaurant(name = "Pepper Stone", rating = 3)
    )
}