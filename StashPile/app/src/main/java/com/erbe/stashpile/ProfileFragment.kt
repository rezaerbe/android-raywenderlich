package com.erbe.stashpile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.erbe.stashpile.usecase.GetRecommendedStockUseCase
import com.erbe.stashpile.usecase.GetStocksUseCase
import com.erbe.stashpile.usecase.GetTotalValueUseCase
import com.erbe.stashpile.usecase.GetUserInformationUseCase
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers

private const val PREFERENCE_IS_FIRST_PROFILE_LAUNCH = "preference_is_first_profile_launch"

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel

    /**
     * Displays an informative  dialog the first time this screen is launched.
     *
     * This block demonstrates the usage of the Fragment's [lifecycleScope] to run an operation that
     * can potentially outlast its lifecycle. Using [lifecycleScope.launchWhenResumed] guarantees
     * that when the Fragment is destroyed, the operation is cancelled.
     */
    init {
        lifecycleScope.launchWhenResumed {
            if (isFirstProfileLaunch()) {
                displayTipDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpProfileInfo()
    }

    private fun setUpViewModel() {
        val factory = ProfileViewModelFactory(
            GetUserInformationUseCase(),
            GetTotalValueUseCase(),
            GetStocksUseCase(),
            GetRecommendedStockUseCase(Dispatchers.IO)
        )
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    private fun setUpProfileInfo() {
        setUpUserInformation()
        setUpTotalValue()
        setUpStocks()
        setUpRecommendedStock()
    }

    private fun setUpUserInformation() {
        viewModel.userInformation.observe(this as LifecycleOwner, Observer { userInformation ->
            nameTextView.text = userInformation.name
            accountNumberTextView.text = userInformation.accountNumber
            phoneNumberTextView.text = userInformation.phoneNumber
        })
    }

    private fun setUpTotalValue() {
        viewModel.totalValue.observe(this as LifecycleOwner, Observer { totalValue ->
            totalValueTextView.text = getString(R.string.profile_total_value, totalValue)
        })
    }

    private fun setUpStocks() {
        viewModel.stocks.observe(this as LifecycleOwner, Observer { stocks ->
            stocksTextView.text = stocks
        })
    }

    private fun setUpRecommendedStock() {
        viewModel.recommendStock.observe(this as LifecycleOwner, Observer { recommendStock ->
            recommendedStockTextView.text = recommendStock
        })
        refreshRecommendedStockButton.setOnClickListener { viewModel.refreshRecommendedStock() }
    }

    private fun isFirstProfileLaunch(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        return sharedPreferences.getBoolean(PREFERENCE_IS_FIRST_PROFILE_LAUNCH, true)
    }

    private fun displayTipDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.profile_tip_title)
            .setMessage(R.string.profile_tip_message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                recordProfileFirstLaunch()
            }
            .show()
    }

    private fun recordProfileFirstLaunch() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedPreferences.edit()
            .putBoolean(PREFERENCE_IS_FIRST_PROFILE_LAUNCH, false)
            .apply()
    }
}