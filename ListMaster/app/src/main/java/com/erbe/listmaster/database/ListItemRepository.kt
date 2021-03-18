package com.erbe.listmaster.database

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.erbe.listmaster.ListMasterApplication
import com.erbe.listmaster.model.ListItem

class ListItemRepository {

    private val listItemDao = ListMasterApplication.database!!.listItemDao()

    fun insertAll(vararg listItems: ListItem) {
        AsyncTask.execute {
            listItemDao.insertAll(*listItems)
        }
    }

    fun getAllByListCategoryId(listCategoryId: Long): LiveData<List<ListItem>> {
        return listItemDao.getAllByListCategoryId(listCategoryId)
    }
}