package com.erbe.listmaster.database

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.erbe.listmaster.ListMasterApplication
import com.erbe.listmaster.model.ListCategory

class ListCategoryRepository {

    private val listCategoryDao: ListCategoryDao
    val listCategories: LiveData<List<ListCategory>>

    init {
        val database = ListMasterApplication.database
        listCategoryDao = database!!.listCategoryDao()
        listCategories = listCategoryDao.getAll()
    }

    fun insertAll(vararg listCategories: ListCategory) {
        AsyncTask.execute {
            listCategoryDao.insertAll(*listCategories)
        }
    }
}