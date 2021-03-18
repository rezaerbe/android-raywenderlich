package com.erbe.tourx.models

sealed class State<out T> {

    /**
     * Data has been completely loaded and there is no more data coming in
     */
    object Complete : State<Nothing>()

    object Loading : State<Nothing>()

    /**
     * Data [data] is available
     */
    data class Loaded<T>(val data: T) : State<T>()

    data class Error(val message: String) : State<Nothing>()
}