package com.erbe.customersurvey.database

import android.content.Context
import androidx.lifecycle.LiveData

class CustomerSurveyRepo(context: Context) {

    private val customerSurveysDao: CustomerSurveysDao by lazy {
        AppDatabase.getDatabase(context)!!.customerSurveysDao()
    }

    suspend fun insertCustomerSurvey(customerSurvey: SurveyListItem.CustomerSurvey) {
        customerSurveysDao.insertCustomerSurvey(customerSurvey)
    }

    fun getAllSurveys(): LiveData<List<SurveyListItem.CustomerSurvey>> {
        return customerSurveysDao.getAllSurveys()
    }

    fun getHappyBreakFastCustomers(): LiveData<List<SurveyListItem.HappyBreakFastView>> {
        return customerSurveysDao.getHappyBreakFastCustomers()
    }

    fun getSadDinnerCustomers(): LiveData<List<SurveyListItem.SadDinnerView>> {
        return customerSurveysDao.getSadDinnerCustomers()
    }

    fun getAverageLunchCustomers(): LiveData<List<SurveyListItem.AverageLunchView>> {
        return customerSurveysDao.getAverageLunchCustomers()
    }

    fun getQuestionOneSadView(): LiveData<List<SurveyListItem.QuestionOneSadView>> {
        return customerSurveysDao.getQuestionOneSadView()
    }
}