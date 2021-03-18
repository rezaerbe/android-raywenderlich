package com.erbe.customersurvey.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SurveyListItem.CustomerSurvey::class], version = 2, exportSchema = false,
    views = [
        SurveyListItem.HappyBreakFastView::class,
        SurveyListItem.AverageLunchView::class,
        SurveyListItem.SadDinnerView::class,
        SurveyListItem.QuestionOneSadView::class
    ])
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            when (INSTANCE) {
                null -> INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "customerSurveysDb")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }

    abstract fun customerSurveysDao(): CustomerSurveysDao
}