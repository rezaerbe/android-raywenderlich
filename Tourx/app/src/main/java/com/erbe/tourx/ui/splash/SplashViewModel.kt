package com.erbe.tourx.ui.splash

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.erbe.tourx.database.AppDatabase
import com.erbe.tourx.entities.Cost
import com.erbe.tourx.entities.Places
import com.erbe.tourx.utils.BaseViewModel
import com.erbe.tourx.utils.LOG_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel
@ViewModelInject constructor(private val database: AppDatabase) : BaseViewModel<Nothing>() {

    fun populateData(places: Places, costs: List<Cost>) {
        val insertPlaceSource = database.getPlaceDao().bulkInsert(places)
            .delay(2, TimeUnit.SECONDS)
            .doOnComplete { Log.i(LOG_TAG, "Completed inserting places into database") }
        val insertCostSource = database.getCostDao().bulkInsert(costs)
            .delay(1, TimeUnit.SECONDS)
            .doOnComplete { Log.i(LOG_TAG, "Completed inserting costs into database") }

        insertPlaceSource.mergeWith(insertCostSource)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onComplete = onCompleteHandler, onError = onErrorHandler)
            .addTo(disposable)
    }
}