package com.erbe.customersurvey.database

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class SurveyListItem {

    abstract val email: String

    @Entity(tableName = "CustomerSurvey")
    data class CustomerSurvey(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        override val email: String,
        val meal: String,
        val questionOneValue: String?,
        val questionTwoValue: String?,
        val questionThreeValue: String?
    ) : SurveyListItem()

    @DatabaseView("SELECT CustomerSurvey.email FROM CustomerSurvey WHERE CustomerSurvey.meal = 'Breakfast'")
    data class HappyBreakFastView(
        override val email: String
    ) : SurveyListItem()

    @DatabaseView("SELECT CustomerSurvey.email FROM CustomerSurvey WHERE CustomerSurvey.meal = 'Lunch'")
    data class AverageLunchView(
        override val email: String
    ) : SurveyListItem()

    @DatabaseView("SELECT CustomerSurvey.email FROM CustomerSurvey WHERE CustomerSurvey.meal = 'Dinner'")
    data class SadDinnerView(
        override val email: String
    ) : SurveyListItem()

    @DatabaseView("SELECT CustomerSurvey.email FROM CustomerSurvey WHERE CustomerSurvey.questionOneValue = 'Sad'")
    data class QuestionOneSadView(
        override val email: String
    ) : SurveyListItem()
}