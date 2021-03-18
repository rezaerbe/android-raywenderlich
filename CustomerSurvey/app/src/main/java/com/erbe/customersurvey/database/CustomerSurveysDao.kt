package com.erbe.customersurvey.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CustomerSurveysDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCustomerSurvey(customerReview: SurveyListItem.CustomerSurvey)

    @Query("SELECT * FROM CustomerSurvey")
    fun getAllSurveys(): LiveData<List<SurveyListItem.CustomerSurvey>>

    @Query("SELECT * FROM HappyBreakFastView")
    fun getHappyBreakFastCustomers(): LiveData<List<SurveyListItem.HappyBreakFastView>>

    @Query("SELECT * FROM AverageLunchView")
    fun getAverageLunchCustomers(): LiveData<List<SurveyListItem.AverageLunchView>>

    @Query("SELECT * FROM SadDinnerView")
    fun getSadDinnerCustomers(): LiveData<List<SurveyListItem.SadDinnerView>>

    @Query("SELECT * FROM QuestionOneSadView")
    fun getQuestionOneSadView(): LiveData<List<SurveyListItem.QuestionOneSadView>>
}