package com.erbe.listmaster.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.erbe.listmaster.databinding.HolderListCategoryItemBinding
import com.erbe.listmaster.model.ListCategory
import com.erbe.listmaster.ui.ListCategoriesActivity
import com.erbe.listmaster.ui.ListCategoryViewModel
import com.erbe.listmaster.ui.ListItemsActivity

data class ListCategoryViewHolder(
    private val holderListCategoryBinding: HolderListCategoryItemBinding,
    private val listCategoriesActivity: ListCategoriesActivity
) : RecyclerView.ViewHolder(holderListCategoryBinding.root) {

    fun setListCategoryItem(listCategory: ListCategory) {
        val listCategoryViewModel = ListCategoryViewModel(listCategory)
        holderListCategoryBinding.listCategoryItem = listCategoryViewModel
        holderListCategoryBinding.executePendingBindings()
        holderListCategoryBinding.categoryName.setOnClickListener {
            val intent = Intent(listCategoriesActivity, ListItemsActivity::class.java)
            intent.putExtra(ListItemsActivity.LIST_CATEGORY_ID, listCategory.id)
            intent.putExtra(ListItemsActivity.CATEGORY_NAME, listCategory.categoryName)
            listCategoriesActivity.startActivity(intent)
        }
    }

}