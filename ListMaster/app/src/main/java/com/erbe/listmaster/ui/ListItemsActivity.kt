package com.erbe.listmaster.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.listmaster.R
import com.erbe.listmaster.adapter.ListItemAdapter
import com.erbe.listmaster.databinding.ActivityListItemsBinding
import com.erbe.listmaster.databinding.ContentListItemsBinding
import com.erbe.listmaster.databinding.DialogAddItemBinding
import com.erbe.listmaster.model.ListCategory
import com.erbe.listmaster.model.ListItem

class ListItemsActivity : AppCompatActivity() {

    companion object {
        val LIST_CATEGORY_ID = "LIST_CATEGORY_ID"
        val CATEGORY_NAME = "CATEGORY_NAME"
    }

    private lateinit var activityListItemsBinding: ActivityListItemsBinding
    private lateinit var contentListItemsBinding: ContentListItemsBinding
    private lateinit var listItemAdapter: ListItemAdapter
    private lateinit var listCategory: ListCategory

    private lateinit var listItemsViewModel: ListItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityListItemsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_list_items
        )

        val extras = intent.extras
        val catName = extras?.getString(CATEGORY_NAME).toString()
        val catId = extras?.getLong(LIST_CATEGORY_ID)!!

        // listCategory = ListCategory(catName, catId)
        listCategory = ListCategory(catName, catId)

        supportActionBar?.title = listCategory.categoryName

        listItemsViewModel = ViewModelProvider(this).get(ListItemsViewModel::class.java)

        // call our function to set up the add fab button logic
        setupAddButton()

        setupRecyclerAdapter()
    }

    /**
     * sets up the Fab button to launch a dialog allowing the user to add an item to their list.
     */
    private fun setupAddButton() {

        activityListItemsBinding.fab.setOnClickListener {

            // Setup the dialog
            val alertDialogBuilder = AlertDialog.Builder(this).setTitle("Title")
            val dialogAddItemBinding = DialogAddItemBinding.inflate(layoutInflater)

            val listItemViewModel = ListItemViewModel(ListItem("", 0, listCategory.id))
            dialogAddItemBinding.listItemViewModel = listItemViewModel

            alertDialogBuilder.setView(dialogAddItemBinding.root)

            // Setup the positive and negative buttons. When the user clicks ok, a record is added to the
            // db, the db is queried and the RecyclerView is updated.
            alertDialogBuilder.setPositiveButton(android.R.string.ok) { dialog: DialogInterface, which: Int ->
                listItemsViewModel.insertAll(listItemViewModel.listItem)
            }
            alertDialogBuilder.setNegativeButton(android.R.string.cancel, null)
            alertDialogBuilder.show()
        }
    }

    private fun setupRecyclerAdapter() {
        val recyclerViewLinearLayoutManager = LinearLayoutManager(this)

        contentListItemsBinding = activityListItemsBinding.listItemsViewInclude!!
        contentListItemsBinding.listItemRecyclerView.layoutManager = recyclerViewLinearLayoutManager
        listItemAdapter = ListItemAdapter(listOf(), this)
        listItemsViewModel.getAllByListCategoryId(listCategory.id).observe(this, Observer { listItems: List<ListItem>? ->
            listItems?.let {
                listItemAdapter.itemList = it
                listItemAdapter.notifyDataSetChanged()
            }
        })
        contentListItemsBinding.listItemRecyclerView.adapter = listItemAdapter
    }
}