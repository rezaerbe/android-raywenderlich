package com.erbe.sunzoid.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.erbe.sunzoid.data.db.entities.DbForecast
import com.erbe.sunzoid.data.db.entities.DbLocationDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Transaction
    suspend fun updateLocationDetails(locationDetails: DbLocationDetails) {
        clearLocationDetails()
        insertLocationDetails(locationDetails)
    }

    @Insert
    suspend fun insertLocationDetails(locationDetails: DbLocationDetails)

    @Transaction
    suspend fun updateForecasts(forecasts: List<DbForecast>) {
        clearForecasts()
        insertAllForecast(forecasts)
    }

    @Insert
    suspend fun insertAllForecast(forecasts: List<DbForecast>)

    @Query("DELETE FROM location_details_table")
    suspend fun clearLocationDetails()

    @Query("DELETE FROM forecasts_table")
    suspend fun clearForecasts()

    @Query("SELECT * FROM location_details_table")
    suspend fun getLocationDetails(): DbLocationDetails?

    @Query("SELECT * FROM forecasts_table")
    fun getForecasts(): Flow<List<DbForecast>>
}