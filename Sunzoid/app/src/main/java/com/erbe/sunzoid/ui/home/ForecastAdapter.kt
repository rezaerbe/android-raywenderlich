package com.erbe.sunzoid.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.sunzoid.R
import com.erbe.sunzoid.ui.home.mapper.ForecastViewState
import com.erbe.sunzoid.util.image_loader.ImageLoader
import kotlinx.android.synthetic.main.forecast_list_item.view.*

class ForecastAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val forecasts: MutableList<ForecastViewState> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = layoutInflater.inflate(R.layout.forecast_list_item, parent, false)
        return ForecastViewHolder(itemView, imageLoader)
    }

    override fun getItemCount() = forecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) =
        holder.setItem(forecasts[position])

    fun setData(forecasts: List<ForecastViewState>) {
        this.forecasts.clear()
        this.forecasts.addAll(forecasts)
        notifyDataSetChanged()
    }

    class ForecastViewHolder(
        itemView: View,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(itemView) {

        fun setItem(forecast: ForecastViewState) = with(itemView) {

            imageLoader.load(forecast.iconUrl, icon)

            currentTemp.text = forecast.temp
            shortDescription.text = forecast.state
            date.text = forecast.date
            windSpeed.text = forecast.windSpeed
            airPressure.text = forecast.pressure
            humidity.text = forecast.humidity
            visibilityDistance.text = forecast.visibility
            predictability.text = forecast.predictability
            minMaxTemp.text = forecast.minMaxTemp
        }
    }
}