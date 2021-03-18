package com.erbe.tourx.ui.placelist

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.erbe.tourx.entities.Places
import com.erbe.tourx.networking.ApiService
import com.erbe.tourx.utils.BaseViewModel
import com.erbe.tourx.utils.LOG_TAG
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PlaceListViewModel
@ViewModelInject constructor(private val service: ApiService) : BaseViewModel<Places>() {

    /**
     * Uses [Observable.ambWith] to combine two [Observable] so only the first [Observable] to emit gets relayed.
     */
    fun loadTheQuickestOne() {
        startLoading()
        val earthSource = service.fetchEarthPlaces()
        val marsSource = service.fetchMarsPlaces()
            .doOnDispose { Log.i(LOG_TAG, "Disposing mars sources") }

        earthSource.ambWith(marsSource)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { recordStartTime() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccessHandler, onError = onErrorHandler)
            .addTo(disposable)
    }

    /**
     * Uses [Observable.zip] underneath so waits for all the [Observable] to complete.
     */
    fun loadAllAtOnce() {
        startLoading()
        service.fetchEarthPlaces()
            .zipWith(service.fetchMarsPlaces())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { recordStartTime() }
            .map {
                return@map listOf(*it.first.toTypedArray(), *it.second.toTypedArray())
            }
            .subscribeBy(onSuccess = onSuccessHandler, onError = onErrorHandler)
            .addTo(disposable)
    }


    /**
     * Emits as soon as there is data available.
     */
    fun loadOnReceive() {
        startLoading()
        service.fetchEarthPlaces()
            .mergeWith(service.fetchMarsPlaces())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { recordStartTime() }
            .subscribeBy(
                onNext = onDataLoadHandler,
                onError = onErrorHandler,
                onComplete = onCompleteHandler
            ).addTo(disposable)
    }


    /**
     * Waits for all the [Observable] to complete before relaying the erroneous [Observable] down the chain.
     */
    fun loadExperimental() {
        startLoading()
        Single.mergeDelayError(
            service.fetchFromExperimentalApi(),
            service.fetchMarsPlaces(),
            service.fetchEarthPlaces()
        ).subscribeOn(Schedulers.io())
            .doOnSubscribe { recordStartTime() }
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribeBy(
                onNext = onDataLoadHandler,
                onComplete = onCompleteHandler,
                onError = onErrorHandler
            )
            .addTo(disposable)
    }

    fun demonstrateSwitchOnNext() {
        disposeCurrentlyRunningStreams()

        // 1
        val outerSource = Observable.interval(3, TimeUnit.SECONDS)
            .doOnNext {
                Log.i(LOG_TAG, "Emitted by OuterSource: $it")
            }

        // 2
        val innerSource = Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe {
                Log.i(LOG_TAG, "Starting InnerSource")
            }

        // 3
        Observable.switchOnNext(
            outerSource.map { return@map innerSource }
        )
            .doOnNext {
                Log.i(LOG_TAG, "Relayed items $it")
            }
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.single())
            .subscribe().addTo(disposable)
    }

    fun demonstrateJoinBehavior() {
        disposeCurrentlyRunningStreams()

        // 1
        val firstObservable = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .map {
                return@map "SOURCE-1 $it"
            }
        // 2
        val secondObservable = Observable.interval(3000, TimeUnit.MILLISECONDS)
            .map { return@map "SOURCE-2 $it" }

        // 3
        val firstWindow = Function<String, Observable<Long>> {
            Observable.timer(0, TimeUnit.SECONDS)
        }
        val secondWindow = Function<String, Observable<Long>> {
            Observable.timer(0, TimeUnit.SECONDS)
        }

        // 4
        val resultSelector = BiFunction<String, String, String> { t1, t2 ->
            return@BiFunction "$t1, $t2"
        }

        //5
        firstObservable.join(secondObservable, firstWindow, secondWindow, resultSelector)
            .doOnNext {
                Log.i(LOG_TAG, it)
            }
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.single())
            .subscribe().addTo(disposable)
    }


    fun demonstrateGroupJoin() {
        disposeCurrentlyRunningStreams()
        // 1
        val leftSource = Observable.interval(1, TimeUnit.SECONDS)
            .map { return@map "SOURCE-1 $it" }

        // 2
        val rightSource = Observable.interval(5, TimeUnit.SECONDS)
            .map { return@map "SOURCE-2 $it" }

        // 3
        val leftWindow = Function<String, Observable<Long>> {
            Observable.timer(0, TimeUnit.SECONDS)
        }

        // 4
        val rightWindow = Function<String, Observable<Long>> {
            Observable.timer(3, TimeUnit.SECONDS)
        }

        // 5
        val resultSelector = BiFunction<String, Observable<String>, Observable<Pair<String, String>>> { t1, t2 ->
            return@BiFunction t2.map {
                return@map Pair(t1, it)
            }
        }

        // 6
        leftSource.groupJoin(rightSource, leftWindow, rightWindow, resultSelector)
            .concatMap {
                return@concatMap it
            }
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.single())
            .doOnNext {
                Log.i(LOG_TAG, it.toString())
            }
            .subscribe().addTo(disposable)
    }
}