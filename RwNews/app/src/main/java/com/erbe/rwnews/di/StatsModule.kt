package com.erbe.rwnews.di

import com.erbe.rwnews.stats.LengthNewsStats
import com.erbe.rwnews.stats.NewsStats
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.ElementsIntoSet

@Module
@InstallIn(FragmentComponent::class)
class StatsModule {

  @Provides
  @ElementsIntoSet // HERE
  fun provideNewsStats(): Set<NewsStats> = setOf(
      LengthNewsStats()
  )
}