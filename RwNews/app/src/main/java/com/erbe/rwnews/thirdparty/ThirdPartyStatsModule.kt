package com.erbe.rwnews.thirdparty

import com.erbe.rwnews.stats.NewsStats
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(FragmentComponent::class)
class ThirdPartyStatsModule {

  @Provides
  @IntoSet // HERE
  fun provideWordsCountNewsStats(): NewsStats = WordCountNewsStats()
}