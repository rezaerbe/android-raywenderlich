package com.erbe.cheesefinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update

@Dao
interface CheeseDao {

    @Query("SELECT * FROM cheeses WHERE name LIKE :name")
    fun findCheese(name: String): List<Cheese>

    @Query("SELECT favorite FROM cheeses WHERE :id LIMIT 1")
    fun isFavorite(id: Long): Int

    @Update
    fun favoriteCheese(cheese: Cheese): Int

    @Insert(onConflict = IGNORE)
    fun insertAll(cheeses: List<Cheese>): List<Long>
}