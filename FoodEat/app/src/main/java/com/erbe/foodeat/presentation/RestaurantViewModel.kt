package com.erbe.foodeat.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erbe.foodeat.domain.GetRestaurants
import com.erbe.foodeat.domain.SaveRestaurants
import com.erbe.foodeat.domain.model.Restaurant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RestaurantViewModel(getRestaurants: GetRestaurants, saveRestaurants: SaveRestaurants) : ViewModel() {

    private val restaurantsSource = getRestaurants()
    private val restaurantSource = getRestaurants().flatMapObservable { Observable.fromIterable(it) }

    private val _restaurantsLiveData = MutableLiveData<Resource<List<Restaurant>>>()
    val restaurantsLiveData: LiveData<Resource<List<Restaurant>>>
        get() = _restaurantsLiveData

    private val _uiLiveData = MutableLiveData<Resource<Unit>>()
    val uiLiveData: LiveData<Resource<Unit>>
        get() = _uiLiveData

    private val disposables = CompositeDisposable()

    init {
        saveRestaurants()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(::getAllRestaurants)
            .addTo(disposables)
    }

    fun getAllRestaurants() {
        _restaurantsLiveData.value = Resource.Loading

        restaurantsSource
            .subscribeOn(Schedulers.io())
            .map { Resource.Success(it) }
            .subscribe(_restaurantsLiveData::postValue)
            .addTo(disposables)
    }

    fun getTopRestaurants() {
        _restaurantsLiveData.value = Resource.Loading

        restaurantSource
            .take(5)
            .showResults()
    }

    fun getLowestRatedRestaurants() {
        _restaurantsLiveData.value = Resource.Loading

        restaurantSource
            .skipWhile { it.rating > 3 }
            .showResults()
    }

    fun setQueryListener(queryObservable: Observable<String>) {
        queryObservable
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .map(::filterSource)
            .subscribe()
    }

    private fun filterSource(query: String) {
        Log.d(this::class.java.simpleName, "Search query: $query")

        _restaurantsLiveData.value = Resource.Loading

        restaurantSource
            .filter { restaurant ->
                if (query.isEmpty()) return@filter true
                restaurant.name.contains(query, true)
            }
            .showResults()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun Observable<Restaurant>.showResults() {
        this.toList()
            .subscribeOn(Schedulers.io())
            .map { Resource.Success(it) }
            .subscribe(_restaurantsLiveData::postValue)
            .addTo(disposables)

        this.ignoreElements()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _uiLiveData.value = Resource.Success(Unit)
            }
            .addTo(disposables)
    }
}