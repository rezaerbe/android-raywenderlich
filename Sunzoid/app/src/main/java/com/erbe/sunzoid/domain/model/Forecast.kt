package com.erbe.sunzoid.domain.model

data class Forecast(
    val id: Long,
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