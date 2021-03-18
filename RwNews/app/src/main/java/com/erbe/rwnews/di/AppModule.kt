package com.erbe.rwnews.di

import com.erbe.rwnews.repository.NewsRepository
import com.erbe.rwnews.repository.impl.MemoryNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

  @Binds
  abstract fun provideNewsRepository(newsRepository: MemoryNewsRepository): NewsRepository
}


