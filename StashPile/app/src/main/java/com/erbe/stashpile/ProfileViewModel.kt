package com.erbe.stashpile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbe.stashpile.usecase.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    getUserInformationUseCase: GetUserInformationUseCase,
    getTotalValueUseCase: GetTotalValueUseCase,
    getStocksUseCase: GetStocksUseCase,
    private val getRecommendedStockUseCase: GetRecommendedStockUseCase
) : ViewModel() {

    val userInformation: LiveData<UserInformation> = getUserInformationUseCase.get()
    val totalValue: LiveData<Double> = getTotalValueUseCase.get()
    val stocks: LiveData<String> = getStocksUseCase.get()
    val recommendStock: LiveData<String> = getRecommendedStockUseCase.recommendedStock

    init {
        getRecommendedStock()
    }

    private fun getRecommendedStock() {
        viewModelScope.launch {
            getRecommendedStockUseCase.get()
        }
    }

    fun refreshRecommendedStock() {
        viewModelScope.launch {
            getRecommendedStockUseCase.refresh()
        }
    }
}