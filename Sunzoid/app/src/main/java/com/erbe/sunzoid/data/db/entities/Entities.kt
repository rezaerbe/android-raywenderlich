package com.erbe.sunzoid.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_details_table")
data class DbLocationDetails(
    val time: String,
    val sunrise: String,
    val sunset: String,
    val title: String,
    @PrimaryKey val id: Int
) {
    companion object {
        val EMPTY = DbLocationDetails("", "", "", "", -1)
    }
}

@Entity(tableName = "forecasts_table")
data class DbForecast(
    @PrimaryKey val id: Long,
    val state: String,
    val windDirection: String,
    val date: String,
    val minTemp: Double,
    val maxTemp: Double,
    val temp: Double,
    val windSpeed: Double,
    val pressure: Double,
    val humidity: Double,
    val visibility: Double,
    val predictability: Int,
    val weatherStateAbbreviation: String
)