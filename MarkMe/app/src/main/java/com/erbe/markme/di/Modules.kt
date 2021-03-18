package com.erbe.markme.di

import androidx.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.erbe.markme.feature.FeatureContract
import com.erbe.markme.feature.presenter.FeaturePresenter
import com.erbe.markme.main.MainContract
import com.erbe.markme.main.presenter.MainPresenter
import com.erbe.markme.model.Student
import com.erbe.markme.model.database.AppDatabase
import com.erbe.markme.repository.AppRepository
import com.erbe.markme.splash.SplashContract
import com.erbe.markme.splash.presenter.SplashPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val applicationModule = module(override = true) {
    factory<SplashContract.Presenter> { (view: SplashContract.View) -> SplashPresenter(view) }
    factory<MainContract.Presenter> { (view: MainContract.View) -> MainPresenter(view) }
    factory<FeatureContract.Presenter<Student>> { (view: FeatureContract.View<Student>) -> FeaturePresenter(view) }
    single<FeatureContract.Model<Student>> { AppRepository }
    single<SharedPreferences> { androidContext().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE) }
    single {
        Room.databaseBuilder(androidContext(),
                AppDatabase::class.java, "app-database").build()
    }
}
