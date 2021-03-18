package com.erbe.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val GET_RECOMMENDED_STOCK_DELAY = 1_000L
private val RECOMMENDED_STOCKS = listOf("UBER", "NFLX", "SQ", "DIS", "MSFT", "BABA", "NKE", "LYFT")
private const val REFRESHING = "Refreshing..."

/**
 * Returns a recommended stock for the user to purchase, and allows for it to be refreshed to get
 * the latest recommendation.
 *
 * This class demonstrates the usage of [ViewModel.viewModelScope] and exposes suspend
 * functions that simulate "long running" operations. When [ProfileViewModel] invokes these methods,
 * it uses its scope to launch them so that when it is cleared, the operations are cancelled.
 */
class GetRecommendedStockUseCase(private val ioDispatcher: CoroutineDispatcher) {

    private val _recommendedStock = MutableLiveData<String>()
    val recommendedStock: LiveData<String>
        get() = _recommendedStock

    suspend fun get() = withContext(ioDispatcher) {
        delay(GET_RECOMMENDED_STOCK_DELAY)
        _recommendedStock.postValue(RECOMMENDED_STOCKS.random())
    }

    suspend fun refresh() = withContext(ioDispatcher) {
        _recommendedStock.postValue(REFRESHING)
        get()
    }
}