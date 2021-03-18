package com.erbe.markme.feature.presenter

import com.erbe.markme.feature.FeatureContract
import com.erbe.markme.model.Student
import com.erbe.markme.utils.ClassSection
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeaturePresenter(private var view: FeatureContract.View<Student>?)
    : FeatureContract.Presenter<Student>, KoinComponent {

    private val repository: FeatureContract.Model<Student> by inject()

    override fun onSave2PrefsClick(data: List<Student>?) {
        data?.let {
            repository.add2Prefs(data = data, callback = { msg ->
                view?.showToastMessage(msg)
            })
        }
    }

    override fun onSave2DbClick(data: List<Student>?) {
        data?.let {
            repository.add2Db(data = data, callback = { msg ->
                view?.showToastMessage(msg)
            })
        }
    }

    override fun loadPersistedData(data: List<Student>, featureType: ClassSection) {
        when (featureType) {
            ClassSection.ATTENDANCE -> repository.fetchFromPrefs(data)
            ClassSection.GRADING -> repository.fetchFromDb(data = data,
                    callback = { loadedData ->
                        view?.onPersistedDataLoaded(loadedData)
                    })
        }
    }

    override fun onViewDestroyed() {
        view = null
    }
}
