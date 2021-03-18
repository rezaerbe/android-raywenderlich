package com.erbe.foodeat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erbe.foodeat.domain.model.Restaurant
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRestaurants(restaurants: List<Restaurant>): Completable

    @Query("SELECT * FROM restaurant ORDER BY rating DESC")
    fun loadAllRestaurants(): Single<List<Restaurant>>
}