package com.erbe.sunzoid.ui.home.mapper

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.erbe.sunzoid.domain.model.Forecast
import com.erbe.sunzoid.domain.model.Location
import com.erbe.sunzoid.util.extensions.round
import kotlin.math.roundToInt

private const val IMAGE_BASE_URL = "https://www.metaweather.com//static/img/weather/png/64/"
private const val KM_IN_MILE = 1.61
private const val ONE_DECIMAL = 1
private const val TWO_DECIMALS = 2

class HomeViewStateMapperImpl : HomeViewStateMapper {

    override fun mapForecastsToViewState(forecasts: List<Forecast>): List<ForecastViewState> {
        return forecasts.map { mapForecastToViewState(it) }
    }

    override fun mapLocationsToViewState(locations: List<Location>): List<LocationViewState> {
        return locations.map { LocationViewState(it.id, it.title) }
    }

    private fun mapForecastToViewState(forecast: Forecast): ForecastViewState {
        return with(forecast) {
            ForecastViewState(
                state,
                windDirection,
                applySpanToLabel("Date", date),
                applySpanToLabel(
                    "${forecast.minTemp.round(ONE_DECIMAL)} — ${forecast.maxTemp.round(
                        ONE_DECIMAL
                    )}", "°C"
                ),
                "${temp.round(ONE_DECIMAL)}°C",
                applySpanToLabel(
                    "Wind speed",
                    "${windSpeed.times(KM_IN_MILE).round(TWO_DECIMALS)} km/h"
                ),
                applySpanToLabel("Pressure", "${pressure.round(ONE_DECIMAL)} mbar"),
                applySpanToLabel("Humidity", "${humidity.roundToInt()}%"),
                applySpanToLabel(
                    "Visibility",
                    "${visibility.times(KM_IN_MILE).round(ONE_DECIMAL)} km"
                ),
                applySpanToLabel("Predictability", "${predictability}%"),
                "$IMAGE_BASE_URL${weatherStateAbbreviation}.png"
            )
        }
    }

    private fun applySpanToLabel(label: String, value: String): SpannableString {
        val spannable = SpannableString("$label $value")

        spannable.setSpan(
            StyleSpan(BOLD),
            0,
            label.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            label.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannable
    }
}