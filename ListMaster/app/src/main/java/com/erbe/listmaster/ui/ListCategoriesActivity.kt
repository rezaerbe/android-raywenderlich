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
import com.erbe.listmaster.adapter.ListCategoryAdapter
import com.erbe.listmaster.databinding.ActivityListCategoriesBinding
import com.erbe.listmaster.databinding.ContentListCategoriesBinding
import com.erbe.listmaster.databinding.DialogAddCategoryBinding
import com.erbe.listmaster.model.ListCategory

class ListCategoriesActivity : AppCompatActivity() {

    private lateinit var activityListsBinding: ActivityListCategoriesBinding
    private lateinit var listCategoryAdapter: ListCategoryAdapter

    private lateinit var contentListsBinding: ContentListCategoriesBinding
    private lateinit var listCategoriesViewModel: ListCategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityListsBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_categories)

        // call our function to set up the add fab button logic
        setupAddButton()

        // Set up our categories viewmodel that accesses the database
        listCategoriesViewModel = ViewModelProvider(this).get(ListCategoriesViewModel::class.java)

        // Set up our recycler adapter
        setupRecyclerAdapter()
    }

    /**
     * sets up the Fab button to launch a dialog allowing the user to add an item to their list.
     */
    private fun setupAddButton() {

        activityListsBinding.fab.setOnClickListener {

            // Set up the dialog
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Title")
            val dialogAddCategoryBinding = DialogAddCategoryBinding.inflate(layoutInflater)

            alertDialogBuilder.setView(dialogAddCategoryBinding.root)

            // Set up the view model that the dialog input fields are bound to
            val listCategoryViewModel = ListCategoryViewModel()
            dialogAddCategoryBinding.listCategoryViewModel = listCategoryViewModel

            // Set up the positive and negative buttons. When the user clicks 'OK', a record is added to the
            // DB, the DB is queried, and the RecyclerView is updated.
            alertDialogBuilder.setPositiveButton(android.R.string.ok) { dialog: DialogInterface, which: Int ->
                listCategoriesViewModel.insertAll(listCategoryViewModel.listCategory)
            }

            alertDialogBuilder.setNegativeButton(android.R.string.cancel, null)
            alertDialogBuilder.show()
        }
    }

    private fun setupRecyclerAdapter() {
        val recyclerViewLinearLayoutManager = LinearLayoutManager(this)
        contentListsBinding = activityListsBinding.contentLists!!
        contentListsBinding.listCategoryRecyclerView.layoutManager = recyclerViewLinearLayoutManager
        listCategoryAdapter = ListCategoryAdapter(listOf(), this)
        listCategoriesViewModel.listCategories.observe(this, Observer { listCategories: List<ListCategory>? ->
            listCategories?.let {
                listCategoryAdapter.categoryList = it
                listCategoryAdapter.notifyDataSetChanged()
            }
        })
        contentListsBinding.listCategoryRecyclerView.adapter = listCategoryAdapter
    }
}