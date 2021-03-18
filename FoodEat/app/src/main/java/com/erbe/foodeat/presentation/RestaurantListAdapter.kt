package com.erbe.foodeat.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.erbe.foodeat.R
import com.erbe.foodeat.domain.model.Restaurant
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantListAdapter : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    private val restaurants = ArrayList<Restaurant>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount() = restaurants.size

    fun updateRestaurants(updatedRestaurants: List<Restaurant>) {
        restaurants.clear()
        restaurants.addAll(updatedRestaurants)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: Restaurant) {
            itemView.apply {
                titleLabel.text = restaurant.name
                ratingLabel.text = context.getString(R.string.rating, restaurant.rating)

                when {
                    restaurant.rating > 3 -> {
                        ratingLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    }
                    restaurant.rating < 3 -> {
                        ratingLabel.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                    }
                    else -> ratingLabel.setTextColor(ContextCompat.getColor(context, R.color.grey))
                }
            }
        }
    }
}