package com.erbe.sunzoid.data.network.model

import com.squareup.moshi.Json

data class ApiLocation(
    val title: String,
    @Json(name = "location_type") val locationType: String,
    @Json(name = "woeid") val id: Int,
    @Json(name = "latt_long") val latitudeLongitude: String
)

data class ApiLocationDetails(
    @Json(name = "consolidated_weather") val forecasts: List<ApiForecast>,
    val time: String,
    @Json(name = "sun_rise") val sunrise: String,
    @Json(name = "sun_set") val sunset: String,
    val title: String,
    @Json(name = "woeid") val id: Int
)

data class ApiForecast(
    val id: Long,
    @Json(name = "weather_state_name") val weatherState: String,
    @Json(name = "weather_state_abbr") val weatherStateAbbreviation: String,
    @Json(name = "wind_direction_compass") val windDirection: String,
    @Json(name = "applicable_date") val date: String,
    @Json(name = "min_temp") val minTemp: Double,
    @Json(name = "max_temp") val maxTemp: Double,
    @Json(name = "the_temp") val temp: Double,
    @Json(name = "wind_speed") val windSpeed: Double,
    @Json(name = "air_pressure") val airPressure: Double,
    @Json(name = "humidity") val humidity: Double,
    @Json(name = "visibility") val visibility: Double,
    @Json(name = "predictability") val predictability: Int
)