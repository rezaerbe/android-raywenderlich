package com.erbe.sunzoid.data.db.mapper

import com.erbe.sunzoid.data.db.entities.DbForecast
import com.erbe.sunzoid.data.db.entities.DbLocationDetails
import com.erbe.sunzoid.domain.model.Forecast
import com.erbe.sunzoid.domain.model.LocationDetails

interface DbMapper {

    fun mapDomainLocationDetailsToDb(locationDetails: LocationDetails): DbLocationDetails

    fun mapDbLocationDetailsToDomain(locationDetails: DbLocationDetails): LocationDetails

    fun mapDomainForecastsToDb(forecasts: List<Forecast>): List<DbForecast>

    fun mapDbForecastsToDomain(forecasts: List<DbForecast>): List<Forecast>
}