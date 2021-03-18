package com.erbe.listmaster.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.erbe.listmaster.database.ListItemRepository
import com.erbe.listmaster.model.ListItem

class ListItemsViewModel(application: Application) : AndroidViewModel(application) {

    private val listItemRepository: ListItemRepository = ListItemRepository()

    fun insertAll(vararg listItems: ListItem) {
        listItemRepository.insertAll(*listItems)
    }

    fun getAllByListCategoryId(listCategoryId: Long): LiveData<List<ListItem>> {
        return listItemRepository.getAllByListCategoryId(listCategoryId)
    }
}