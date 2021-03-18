package com.erbe.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.delay

private const val GET_STOCKS_DELAY = 1_000L
private val STOCKS = listOf("GOOG", "AAPL", "AMZN", "TSLA", "TWTR", "FB")

/**
 * Returns the user's stocks as a [String] in the form "stock1, stock2, stock3".
 *
 * This class demonstrates combining exposed [LiveData] with [Transformations] by transforming
 * a [LiveData] instance using the [LiveData.switchMap] method.
 *
 * Note that the transformation method can be a suspend function.
 */
class GetStocksUseCase {

    fun get(): LiveData<String> = getStocks().switchMap { stocks ->
        liveData {
            emit(stocks.joinToString())
        }
    }

    private fun getStocks(): LiveData<List<String>> = liveData {
        delay(GET_STOCKS_DELAY)
        emit(STOCKS)
    }
}