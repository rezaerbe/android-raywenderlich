package com.erbe.sunzoid.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.erbe.sunzoid.R
import com.erbe.sunzoid.ui.home.mapper.LocationViewState

class LocationAdapter(
    private val layoutInflater: LayoutInflater,
    private val onLocationClick: (LocationViewState) -> Unit
) : BaseAdapter() {

    private val locations = mutableListOf<LocationViewState>()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = layoutInflater.inflate(R.layout.location_list_item, parent, false)
        itemView.findViewById<TextView>(R.id.location).text = locations[position].location
        itemView.setOnClickListener { onLocationClick(locations[position]) }
        return itemView
    }

    override fun getItem(position: Int) = locations[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = locations.size

    fun setData(locations: List<LocationViewState>) {
        this.locations.clear()
        this.locations.addAll(locations)
        notifyDataSetChanged()
    }
}