package com.erbe.tourx.di

import android.content.Context
import androidx.room.Room
import com.erbe.tourx.database.AppDatabase
import com.erbe.tourx.networking.ApiService
import com.erbe.tourx.networking.MockApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "Tourx.db")
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(database: AppDatabase): ApiService {
        return MockApiService(database)
    }
}