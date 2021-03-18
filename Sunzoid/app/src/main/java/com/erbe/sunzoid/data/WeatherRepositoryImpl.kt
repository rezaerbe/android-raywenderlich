package com.erbe.sunzoid.data

import com.erbe.sunzoid.data.db.dao.ForecastDao
import com.erbe.sunzoid.data.db.mapper.DbMapper
import com.erbe.sunzoid.data.network.client.WeatherApiClient
import com.erbe.sunzoid.data.network.mapper.ApiMapper
import com.erbe.sunzoid.domain.model.Location
import com.erbe.sunzoid.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherApiClient: WeatherApiClient,
    private val apiMapper: ApiMapper,
    private val backgroundDispatcher: CoroutineDispatcher,
    private val forecastDao: ForecastDao,
    private val dbMapper: DbMapper
) : WeatherRepository {

    override suspend fun findLocation(location: String) = withContext(backgroundDispatcher) {
        try {
            weatherApiClient.findLocation(location)
                .map(apiMapper::mapApiLocationToDomain)
        } catch (error: Throwable) {
            emptyList<Location>()
        }
    }

    override suspend fun fetchLocationDetails(id: Int) = withContext(backgroundDispatcher) {
        val locationDetails = try {
            apiMapper.mapApiLocationDetailsToDomain(
                weatherApiClient.getLocationDetails(id)
            )
        } catch (error: Throwable) {
            null
        }

        if (locationDetails != null) {
            forecastDao.updateLocationDetails(dbMapper.mapDomainLocationDetailsToDb(locationDetails))
            forecastDao.updateForecasts(dbMapper.mapDomainForecastsToDb(locationDetails.forecasts))
        }
    }

    override fun getForecasts() =
        forecastDao
            .getForecasts()
            .map { dbMapper.mapDbForecastsToDomain(it) }
}