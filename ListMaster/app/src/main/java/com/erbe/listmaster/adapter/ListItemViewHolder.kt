package com.erbe.listmaster.adapter

import androidx.recyclerview.widget.RecyclerView
import com.erbe.listmaster.databinding.HolderListItemBinding
import com.erbe.listmaster.model.ListItem
import com.erbe.listmaster.ui.ListItemViewModel
import com.erbe.listmaster.ui.ListItemsActivity

data class ListItemViewHolder(
    private val holderListItemBinding: HolderListItemBinding,
    val listItemsActivity: ListItemsActivity
) : RecyclerView.ViewHolder(holderListItemBinding.root) {

    fun setListItem(listItem: ListItem) {
        holderListItemBinding.listItemViewModel = ListItemViewModel(listItem)
        holderListItemBinding.executePendingBindings()
    }
}