package com.erbe.tourx.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.erbe.tourx.models.State

abstract class BaseActivity<T> : AppCompatActivity(), Observer<State<T>> {

    override fun onChanged(state: State<T>?) {
        when (state) {
            is State.Loaded -> onDataLoaded(state.data)
            is State.Complete -> onTaskComplete()
            is State.Error -> onError(state.message)
            is State.Loading -> onTaskLoading()
        }
    }

    open fun onTaskLoading() {
    }

    abstract fun onDataLoaded(data: T)

    abstract fun onTaskComplete()

    abstract fun onError(message: String)
}