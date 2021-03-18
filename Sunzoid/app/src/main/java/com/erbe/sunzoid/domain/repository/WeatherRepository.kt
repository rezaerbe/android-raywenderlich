package com.erbe.sunzoid.domain.repository

import com.erbe.sunzoid.domain.model.Forecast
import com.erbe.sunzoid.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun findLocation(location: String): List<Location>

    suspend fun fetchLocationDetails(id: Int)

    fun getForecasts(): Flow<List<Forecast>>
}