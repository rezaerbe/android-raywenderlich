package com.erbe.customersurvey.customersurveys

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.erbe.customersurvey.database.CustomerSurveyRepo
import com.erbe.customersurvey.database.SurveyListItem
import kotlinx.coroutines.launch

class CustomerSurveyViewModel(application: Application) : AndroidViewModel(application) {

    private val customerSurveyRepo = CustomerSurveyRepo((application))

    val customerSurveys: LiveData<List<SurveyListItem.CustomerSurvey>> by lazy {
        customerSurveyRepo.getAllSurveys()
    }
    val happyBreakfastCustomers: LiveData<List<SurveyListItem.HappyBreakFastView>> by lazy {
        customerSurveyRepo.getHappyBreakFastCustomers()
    }
    val averageLunchCustomers: LiveData<List<SurveyListItem.AverageLunchView>> by lazy {
        customerSurveyRepo.getAverageLunchCustomers()
    }
    val sadDinnerCustomers: LiveData<List<SurveyListItem.SadDinnerView>> by lazy {
        customerSurveyRepo.getSadDinnerCustomers()
    }

    val sadQuestionOneCustomers: LiveData<List<SurveyListItem.QuestionOneSadView>> by lazy {
        customerSurveyRepo.getQuestionOneSadView()
    }

    fun insertCustomerSurvey(customerSurvey: SurveyListItem.CustomerSurvey) {
        viewModelScope.launch {
            customerSurveyRepo.insertCustomerSurvey(customerSurvey)
        }
    }
}