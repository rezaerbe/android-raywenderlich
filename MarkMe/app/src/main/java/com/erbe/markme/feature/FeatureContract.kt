package com.erbe.markme.feature

import com.erbe.markme.utils.ClassSection

interface FeatureContract {
    interface View<T> {
        fun showToastMessage(msg: String)
        fun onPersistedDataLoaded(data: List<T>)
    }

    interface Presenter<T> {
        fun onViewDestroyed()
        fun loadPersistedData(data: List<T>, featureType: ClassSection)
        // User Actions
        fun onSave2PrefsClick(data: List<T>?)
        fun onSave2DbClick(data: List<T>?)
    }

    interface Model<T> {
        fun add2Db(data: List<T>, callback: (String) -> Unit = {})
        fun add2Prefs(data: List<T>, callback: (String) -> Unit)
        fun fetchFromDb(data: List<T>, callback: (List<T>) -> Unit)
        fun fetchFromPrefs(data: List<T>): List<T>
    }
}