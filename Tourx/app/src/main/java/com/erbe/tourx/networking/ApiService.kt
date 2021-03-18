package com.erbe.tourx.networking

import com.erbe.tourx.entities.Cost
import com.erbe.tourx.entities.Place
import com.erbe.tourx.entities.Places
import io.reactivex.Single

interface ApiService {
    fun fetchMarsPlaces(): Single<Places>

    fun fetchEarthPlaces(): Single<Places>

    fun fetchFromExperimentalApi(): Single<Places>

    fun fetchPlaceById(id: Int): Single<Place>

    fun fetchCostById(id: Int): Single<Cost>
}