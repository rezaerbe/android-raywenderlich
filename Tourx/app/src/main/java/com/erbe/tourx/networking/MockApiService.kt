package com.erbe.tourx.networking

import com.erbe.tourx.database.AppDatabase
import com.erbe.tourx.entities.Cost
import com.erbe.tourx.entities.Place
import com.erbe.tourx.entities.Places
import com.erbe.tourx.utils.MIN_DELAY_IN_MS
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class MockApiService(private val db: AppDatabase) : ApiService {

    /**
     * Simulates a slower operation than [fetchEarthPlaces]
     */
    override fun fetchMarsPlaces(): Single<List<Place>> {
        return db.getPlaceDao().loadPlacesByPlanet("Mars")
            .delay(MIN_DELAY_IN_MS + 2000, TimeUnit.MILLISECONDS)
    }

    /**
     * Faster operation as compared to [fetchMarsPlaces]
     */
    override fun fetchEarthPlaces(): Single<List<Place>> {
        return db.getPlaceDao().loadPlacesByPlanet("Earth")
            .delay(MIN_DELAY_IN_MS, TimeUnit.MILLISECONDS)
    }

    /**
     * Immediately returns an error.
     */
    override fun fetchFromExperimentalApi(): Single<Places> {
        return Single.error { RuntimeException("Error while loading experimental features.") }
    }

    override fun fetchCostById(id: Int): Single<Cost> {
        return db.getCostDao().loadPlaceById(id)
            .delay(MIN_DELAY_IN_MS + 500, TimeUnit.MILLISECONDS)
    }

    override fun fetchPlaceById(id: Int): Single<Place> {
        return db.getPlaceDao().loadPlaceById(id)
            .delay(MIN_DELAY_IN_MS + 1000, TimeUnit.MILLISECONDS)
    }
}