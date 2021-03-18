package com.erbe.tourx.ui.placelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.tourx.R
import com.erbe.tourx.entities.Place
import kotlinx.android.synthetic.main.item_places.view.*

class PlaceAdapter
constructor(private val onClickAction: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val places = mutableListOf<Place>()

    fun reset() {
        this.places.clear()
        notifyDataSetChanged()
    }

    fun add(places: List<Place>) {
        this.places.addAll(places)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_places, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val place = places[position]
        holder.itemView.apply {
            nameTextView.text = place.name
            locationTextView.text = place.planet
            setOnClickListener { onClickAction(place.placeId) }
        }
    }

    override fun getItemCount() = places.size

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}