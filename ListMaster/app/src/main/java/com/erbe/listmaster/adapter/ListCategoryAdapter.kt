package com.erbe.listmaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.listmaster.databinding.HolderListCategoryItemBinding
import com.erbe.listmaster.model.ListCategory
import com.erbe.listmaster.ui.ListCategoriesActivity

data class ListCategoryAdapter(
    var categoryList: List<ListCategory>,
    private val listCategoriesActivity: ListCategoriesActivity
) : RecyclerView.Adapter<ListCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val holderListCategoryItemBinding = HolderListCategoryItemBinding.inflate(layoutInflater, parent, false)
        return ListCategoryViewHolder(holderListCategoryItemBinding, listCategoriesActivity)
    }

    override fun onBindViewHolder(holder: ListCategoryViewHolder, position: Int) {
        holder.setListCategoryItem(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}