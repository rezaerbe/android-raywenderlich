package com.erbe.rwnews.di

import com.erbe.rwnews.presenter.NewsDetailPresenter
import com.erbe.rwnews.presenter.NewsListPresenter
import com.erbe.rwnews.presenter.impl.NewsDetailPresenterImpl
import com.erbe.rwnews.presenter.impl.NewsListPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
abstract class FeatureModule {

  @Binds
  abstract fun provideNewsListPresenter(newsRepository: NewsListPresenterImpl): NewsListPresenter

  @Binds
  abstract fun provideNewsDetailPresenter(newsRepository: NewsDetailPresenterImpl): NewsDetailPresenter
}