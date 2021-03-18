package com.erbe.droidwiki.dagger

import com.erbe.droidwiki.network.Homepage
import com.erbe.droidwiki.network.Wiki
import com.erbe.droidwiki.network.WikiApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WikiModule {

    @Provides
    @Singleton
    fun provideHomepage(api: WikiApi) = Homepage(api)

    @Provides
    @Singleton
    fun provideWiki(api: WikiApi) = Wiki(api)
}