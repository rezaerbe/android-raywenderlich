package com.erbe.gobuy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.gobuy.R
import com.erbe.gobuy.databinding.ActivityGroceryListBinding
import com.erbe.gobuy.model.GroceryItem
import com.erbe.gobuy.viewmodel.GroceryListViewModel
import com.google.android.material.snackbar.Snackbar

class GroceryListActivity : AppCompatActivity(), NewItemDialogFragment.NewItemDialogListener {

    lateinit var viewModel: GroceryListViewModel
    private lateinit var binding: ActivityGroceryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GoBuy)

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(GroceryListViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_grocery_list)

        binding.rvGroceryList.layoutManager = LinearLayoutManager(this)

        binding.rvGroceryList.adapter = GroceryAdapter(viewModel.groceryListItems, this, ::editGroceryItem, ::deleteGroceryItem)

        binding.addItemButton.setOnClickListener {
            addGroceryItem()
        }
    }

    private fun addGroceryItem() {
        val newFragment = NewItemDialogFragment.newInstance(R.string.add_new_item_dialog_title, null)
        newFragment.show(supportFragmentManager, "newItem")
    }

    private fun editGroceryItem(position: Int) {
        Log.d("GoBuy", "edit")
        val newFragment = NewItemDialogFragment.newInstance(R.string.edit_item_dialog_title,
            position)
        newFragment.show(supportFragmentManager, "newItem")
    }

    private fun deleteGroceryItem(position: Int) {
        Log.d("GoBuy", "delete")
        viewModel.removeItem(position)
        binding.totalAmount = viewModel.getTotal().toString()
        binding.rvGroceryList.adapter?.notifyDataSetChanged()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, item: GroceryItem, isEdit: Boolean, position: Int?) {
        if (!isEdit) {
            viewModel.groceryListItems.add(item)
        } else {
            viewModel.updateItem(position!!, item)
            binding.rvGroceryList.adapter?.notifyDataSetChanged()
        }
        binding.totalAmount = String.format("%.2f", viewModel.getTotal())
        Snackbar.make(binding.addItemButton, "Item Added Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Snackbar.make(binding.addItemButton, "Nothing Added", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }
}