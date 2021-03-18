package com.erbe.tourx.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import com.erbe.tourx.entities.Cost
import com.erbe.tourx.models.PlaceDetail
import com.erbe.tourx.networking.ApiService
import com.erbe.tourx.utils.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

class PlaceDetailViewModel
@ViewModelInject constructor(private val service: ApiService) : BaseViewModel<PlaceDetail>() {

    fun loadPlaceDetails(id: Int) {
        startLoading()
        val costSource = service.fetchCostById(id)
        val placeSource = service.fetchPlaceById(id)

        costSource.zipWith(placeSource)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { recordStartTime() }
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map PlaceDetail(cost = it.first, place = it.second)
            }
            .subscribeBy(onSuccess = onSuccessHandler, onError = onErrorHandler)
            .addTo(disposable)
    }

    fun calculateTravelCost(cost: Cost, passengerCount: Int, isTwoWayTravel: Boolean) =
        cost.price.times(passengerCount).times(if (isTwoWayTravel) 2 else 1)

}