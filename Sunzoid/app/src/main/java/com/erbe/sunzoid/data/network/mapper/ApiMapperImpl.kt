package com.erbe.sunzoid.data.network.mapper

import com.erbe.sunzoid.data.network.model.ApiForecast
import com.erbe.sunzoid.data.network.model.ApiLocation
import com.erbe.sunzoid.data.network.model.ApiLocationDetails
import com.erbe.sunzoid.domain.model.Forecast
import com.erbe.sunzoid.domain.model.Location
import com.erbe.sunzoid.domain.model.LocationDetails

class ApiMapperImpl : ApiMapper {

    override fun mapApiLocationDetailsToDomain(apiLocationDetails: ApiLocationDetails): LocationDetails {
        return with(apiLocationDetails) {
            LocationDetails(
                forecasts.map { mapApiForecastToDomain(it) },
                time,
                sunrise,
                sunset,
                title,
                id
            )
        }
    }

    override fun mapApiLocationToDomain(apiLocation: ApiLocation) = Location(
        apiLocation.id,
        apiLocation.title
    )

    private fun mapApiForecastToDomain(apiForecast: ApiForecast) = with(apiForecast) {
        Forecast(
            id,
            weatherState,
            windDirection,
            date,
            minTemp,
            maxTemp,
            temp,
            windSpeed,
            airPressure,
            humidity,
            visibility,
            predictability,
            weatherStateAbbreviation
        )
    }
}