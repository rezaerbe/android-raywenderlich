package com.erbe.sunzoid.ui.home.mapper

import com.erbe.sunzoid.domain.model.Forecast
import com.erbe.sunzoid.domain.model.Location

interface HomeViewStateMapper {

    fun mapForecastsToViewState(forecasts: List<Forecast>): List<ForecastViewState>

    fun mapLocationsToViewState(locations: List<Location>): List<LocationViewState>
}