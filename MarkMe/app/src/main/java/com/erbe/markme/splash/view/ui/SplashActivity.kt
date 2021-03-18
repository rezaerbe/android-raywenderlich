package com.erbe.markme.splash.view.ui

import androidx.appcompat.app.AppCompatActivity
import com.erbe.markme.main.view.ui.MainActivity
import com.erbe.markme.splash.SplashContract
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val splashPresenter: SplashContract.Presenter by inject { parametersOf(this) }

    override fun onResume() {
        super.onResume()

        startActivity<MainActivity>()
        splashPresenter.onViewCreated()
    }

    override fun finishView() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        splashPresenter.onViewDestroyed()
    }
}
