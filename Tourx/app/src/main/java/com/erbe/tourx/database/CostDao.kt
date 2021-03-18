package com.erbe.tourx.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erbe.tourx.entities.Cost
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(cost: List<Cost>): Completable

    @Query("SELECT * FROM Cost WHERE placeId=:id")
    fun loadPlaceById(id: Int): Single<Cost>
}