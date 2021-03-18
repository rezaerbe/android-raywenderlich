package com.erbe.listmaster.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.erbe.listmaster.database.ListCategoryRepository
import com.erbe.listmaster.model.ListCategory

class ListCategoriesViewModel(application: Application) : AndroidViewModel(application) {

    private val listCategoryRepository: ListCategoryRepository = ListCategoryRepository()
    val listCategories: LiveData<List<ListCategory>> = listCategoryRepository.listCategories

    fun insertAll(vararg listCategory: ListCategory) {
        listCategoryRepository.insertAll(*listCategory)
    }
}