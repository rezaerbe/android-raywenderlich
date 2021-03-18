package com.erbe.sunzoid.ui.home.mapper

import android.text.SpannableString

data class ForecastViewState(
    val state: String,
    val windDirection: String,
    val date: SpannableString,
    val minMaxTemp: SpannableString,
    val temp: String,
    val windSpeed: SpannableString,
    val pressure: SpannableString,
    val humidity: SpannableString,
    val visibility: SpannableString,
    val predictability: SpannableString,
    val iconUrl: String
)

data class LocationViewState(
    val id: Int,
    val location: String
)