package com.erbe.tourx.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erbe.tourx.entities.Place
import com.erbe.tourx.entities.Places
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(places: Places): Completable

    @Query("SELECT * FROM Place WHERE planet=:planet")
    fun loadPlacesByPlanet(planet: String): Single<Places>

    @Query("SELECT * FROM Place WHERE placeId=:id")
    fun loadPlaceById(id: Int): Single<Place>
}