package com.erbe.stashpile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erbe.stashpile.usecase.GetRecommendedStockUseCase
import com.erbe.stashpile.usecase.GetStocksUseCase
import com.erbe.stashpile.usecase.GetTotalValueUseCase
import com.erbe.stashpile.usecase.GetUserInformationUseCase

class ProfileViewModelFactory(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getTotalValueUseCase: GetTotalValueUseCase,
    private val getStocksUseCase: GetStocksUseCase,
    private val recommendedStockUseCase: GetRecommendedStockUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(getUserInformationUseCase, getTotalValueUseCase, getStocksUseCase, recommendedStockUseCase) as T
    }
}