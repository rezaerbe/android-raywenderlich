package com.erbe.droidwiki.dagger

import com.erbe.droidwiki.network.Homepage
import com.erbe.droidwiki.network.Wiki
import com.erbe.droidwiki.ui.homepage.HomepagePresenter
import com.erbe.droidwiki.ui.homepage.HomepagePresenterImpl
import com.erbe.droidwiki.ui.search.EntryPresenter
import com.erbe.droidwiki.ui.search.EntryPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideHomepagePresenter(homepage: Homepage): HomepagePresenter = HomepagePresenterImpl(homepage)

    @Provides
    @Singleton
    fun provideEntryPresenter(wiki: Wiki): EntryPresenter = EntryPresenterImpl(wiki)
}