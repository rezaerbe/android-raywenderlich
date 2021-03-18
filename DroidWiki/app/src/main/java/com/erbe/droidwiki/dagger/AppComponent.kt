package com.erbe.droidwiki.dagger

import com.erbe.droidwiki.ui.homepage.HomepageActivity
import com.erbe.droidwiki.ui.search.SearchActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    PresenterModule::class,
    NetworkModule::class,
    WikiModule::class])
interface AppComponent {
    fun inject(target: HomepageActivity)
    fun inject(target: SearchActivity)
}