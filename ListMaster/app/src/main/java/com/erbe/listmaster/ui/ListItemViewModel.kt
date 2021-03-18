package com.erbe.listmaster.ui

import com.erbe.listmaster.model.ListItem

data class ListItemViewModel(var listItem: ListItem) {

    fun setPriority(priority: String) {
        if (priority.isNotEmpty()) {
            listItem.itemPriority = Integer.valueOf(priority)
        }
    }

    fun getPriority(): String {
        return listItem.itemPriority.toString()
    }
}