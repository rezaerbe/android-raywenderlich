package com.erbe.tourx.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.NumberPicker
import androidx.activity.viewModels
import com.erbe.tourx.R
import com.erbe.tourx.models.PlaceDetail
import com.erbe.tourx.ui.BaseActivity
import com.erbe.tourx.utils.LOG_TAG
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_place_detail.*

@AndroidEntryPoint
class PlaceDetailActivity : BaseActivity<PlaceDetail>() {

    companion object {
        private const val PLACE_ID = "PLACE_ID"

        @JvmStatic
        fun start(context: Context, id: Int) {
            context.startActivity(
                Intent(context, PlaceDetailActivity::class.java).apply { putExtra(PLACE_ID, id) }
            )
        }
    }

    private val disposable = CompositeDisposable()
    private val viewModel: PlaceDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        setupNumberPicker()

        val id = intent.getIntExtra(PLACE_ID, Integer.MIN_VALUE)

        viewModel.loadPlaceDetails(id)
        viewModel.result.observe(this, this)
    }

    private fun setupNumberPicker() {
        with(numberOfPassengerPicker) {
            minValue = 1
            maxValue = 20
        }
    }

    override fun onTaskLoading() {
        progressBar.visibility = View.VISIBLE
        detailContainer.visibility = View.GONE
    }

    override fun onDataLoaded(data: PlaceDetail) {
        loadDataInUI(data)
    }

    override fun onTaskComplete() {
        progressBar.visibility = View.GONE
    }

    override fun onError(message: String) {
        detailContainer.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun loadDataInUI(placeDetail: PlaceDetail) {
        with(placeDetail) {
            detailContainer.visibility = View.VISIBLE
            nameTextView.text = place.name
            planetTextView.text = place.planet
            costTextView.text = cost.price.toString()

            val isTwoWayTravelObservable = twoWayTravelCheckbox.toObservable()
            val totalPassengerObservable = numberOfPassengerPicker.toObservable()
            // combineUsingConcat(isTwoWayTravelObservable, totalPassengerObservable)
            combineUsingCombineLatest(this, isTwoWayTravelObservable, totalPassengerObservable)

        }
    }

    /**
     * Uses concat operator to combine the two streams.
     * This does not work as concat operator maintains order and since the both observables are "infinite",
     * as soon as the first observables emits, the second observables never gets a chance to emit.
     */
    private fun combineUsingConcat(
        booleanObservable: Observable<Boolean>,
        integerObservable: Observable<Int>
    ): Disposable {
        return Observable.concat(booleanObservable, integerObservable)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { input ->
                Log.i(LOG_TAG, input.toString())
            }).addTo(disposable)
    }

    /**
     * Using combineLatest, the latest emission from the Observables are relayed down the chain.
     */
    private fun combineUsingCombineLatest(
        data: PlaceDetail, booleanObservable: Observable<Boolean>,
        integerObservable: Observable<Int>
    ) {
        Observable.combineLatest<Boolean, Int, Pair<Boolean, Int>>(
            booleanObservable,
            integerObservable,
            BiFunction { t1, t2 ->
                return@BiFunction Pair(t1, t2)
            })
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { input ->
                val passengerCount = input.second
                val isTwoWayTravel = input.first
                resultTextView.text =
                    viewModel.calculateTravelCost(data.cost, passengerCount, isTwoWayTravel)
                        .toString()
            }).addTo(disposable)
    }


    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    /**
     * Converts the checked change event of [CheckBox] to streams
     */
    private fun CheckBox.toObservable(): Observable<Boolean> {
        return Observable.create<Boolean> {
            setOnCheckedChangeListener { _, isChecked ->
                it.onNext(isChecked)
            }
        }.startWith(isChecked)
    }

    /**
     * Converts the value change events of [NumberPicker] to streams.
     */
    private fun NumberPicker.toObservable(): Observable<Int> {
        return Observable.create<Int> {
            setOnValueChangedListener { _, _, newVal ->
                it.onNext(newVal)
            }
        }.startWith(value)
    }
}