package com.erbe.markme.main.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erbe.markme.R
import com.erbe.markme.feature.view.ui.FeatureActivity
import com.erbe.markme.main.MainContract
import com.erbe.markme.utils.ClassSection
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

const val FEATURE_CATEGORY = "categoryName"

class MainActivity : AppCompatActivity(), MainContract.View {

    private val cardAtt by lazy { activity_main__cardview__attendance }
    private val cardGrading by lazy { activity_main__cardview__grading }
    private val mainPresenter: MainContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardAtt.setOnClickListener {
            mainPresenter.onAttendanceOptionClick()
        }
        cardGrading.setOnClickListener {
            mainPresenter.onGradingOptionClick()
        }
    }

    override fun navigateTo(section: ClassSection) {
        when (section) {
            ClassSection.ATTENDANCE -> {
                Timber.d("'Attendance' clicked")
                startActivity<FeatureActivity>(FEATURE_CATEGORY to ClassSection.ATTENDANCE)
            }
            ClassSection.GRADING -> {
                Timber.d("'Grading' clicked")
                startActivity<FeatureActivity>(FEATURE_CATEGORY to ClassSection.GRADING)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mainPresenter.onViewDestroyed()
    }
}
