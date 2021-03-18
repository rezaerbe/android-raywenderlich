package com.erbe.listmaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.listmaster.databinding.HolderListItemBinding
import com.erbe.listmaster.model.ListItem
import com.erbe.listmaster.ui.ListItemsActivity

data class ListItemAdapter(
    var itemList: List<ListItem>,
    private val listItemsActivity: ListItemsActivity
) : RecyclerView.Adapter<ListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val holderListItemBinding = HolderListItemBinding.inflate(layoutInflater, parent, false)
        return ListItemViewHolder(holderListItemBinding, listItemsActivity)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.setListItem(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}