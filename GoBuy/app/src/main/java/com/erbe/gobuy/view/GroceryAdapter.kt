package com.erbe.gobuy.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.erbe.gobuy.R
import com.erbe.gobuy.databinding.GroceryListItemBinding
import com.erbe.gobuy.model.GroceryItem

class GroceryAdapter(val items: ArrayList<GroceryItem>, val context: Context,
                     val itemEditListener: (position: Int) -> Unit,
                     val itemDeleteListener: (position: Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: GroceryListItemBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.grocery_list_item,
            parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val description = item.amount.toString() + "x: " + item.itemName
        holder.bind(items[position])
        holder.binding.buttonEdit.setOnClickListener { itemEditListener(position) }
        holder.binding.buttonDelete.setOnClickListener { itemDeleteListener(position) }
    }

    // Gets the number of groceries in the list
    override fun getItemCount() = items.size
}

class ViewHolder(val binding: GroceryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GroceryItem) {
        binding.apply {
            itemName = "${item.amount}x: ${item.itemName}"
            price = item.price.toString()
        }
    }
}