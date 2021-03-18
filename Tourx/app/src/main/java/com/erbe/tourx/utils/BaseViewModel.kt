package com.erbe.tourx.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erbe.tourx.models.State
import io.reactivex.disposables.CompositeDisposable
import java.util.*

open class BaseViewModel<T> : ViewModel() {

    private var startTime: Long = Calendar.getInstance().timeInMillis
    protected var disposable = CompositeDisposable()

    protected val _result = MutableLiveData<State<T>>()
    val result: LiveData<State<T>>
        get() = _result

    protected val onErrorHandler: (Throwable) -> Unit = {
        Log.i(LOG_TAG, "onErrorHandler after: " + diffWith() + "s")
        _result.value = State.Error(it.message ?: "Failed to load data.")
    }

    protected val onDataLoadHandler: (T) -> Unit = {
        Log.i(LOG_TAG, "onDataLoadHandler after: " + diffWith() + "s")
        _result.value = State.Loaded(it)
    }

    protected val onCompleteHandler: () -> Unit = {
        Log.i(LOG_TAG, "completeHandler after: " + diffWith() + "s")
        _result.value = State.Complete
    }

    protected val onSuccessHandler: (T) -> Unit = {
        Log.i(LOG_TAG, "onSuccessHandler callback after: " + diffWith() + "s")
        onDataLoadHandler(it)
        onCompleteHandler()
    }

    protected fun recordStartTime() {
        startTime = Calendar.getInstance().timeInMillis
    }

    private fun diffWith(): Long {
        return (Calendar.getInstance().timeInMillis - startTime) / 1000
    }

    protected fun startLoading() {
        Log.i(LOG_TAG, "--------------")
        disposeCurrentlyRunningStreams()
        _result.value = State.Loading
    }

    fun disposeCurrentlyRunningStreams() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}