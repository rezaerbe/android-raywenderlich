package com.erbe.learningcompanion.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.learningcompanion.R
import com.erbe.learningcompanion.data.FilterOption
import com.erbe.learningcompanion.presentation.CoursesViewModel
import com.erbe.learningcompanion.ui.list.CoursesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_courses.*

@AndroidEntryPoint
class CoursesActivity : AppCompatActivity() {

    private val viewModel: CoursesViewModel by viewModels()
    private val adapter by lazy { CoursesAdapter() }
    private var nightModeActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LearningCompanion)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        initCourseList()
        subscribeToData()
        observeFilterChanges()
    }

    private fun initCourseList() {
        courseList.layoutManager = LinearLayoutManager(this)
        courseList.itemAnimator = DefaultItemAnimator()
        courseList.adapter = adapter
    }

    private fun subscribeToData() {
        // Subscribe to get the data from the ViewModel
        viewModel.courseUiModel.observe(this) {
            adapter.setCourses(it.courses)
            updateFilter(it.filter)
        }

        viewModel.darkThemeEnabled.observe(this) { nightModeActive ->
            this.nightModeActive = nightModeActive

            val defaultMode = if (nightModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(defaultMode)
        }
    }

    private fun observeFilterChanges() {
        filterBeginner.setOnCheckedChangeListener { _, isChecked ->
            viewModel.enableBeginnerFilter(isChecked)
        }

        filterAdvanced.setOnCheckedChangeListener { _, isChecked ->
            viewModel.enableAdvancedFilter(isChecked)
        }

        filterCompleted.setOnCheckedChangeListener { _, isChecked ->
            viewModel.enableCompletedFilter(isChecked)
        }
    }

    private fun updateFilter(filter: FilterOption.Filter) {
        filterBeginner.isChecked = filter == FilterOption.Filter.BEGINNER ||
                filter == FilterOption.Filter.BEGINNER_ADVANCED ||
                filter == FilterOption.Filter.BEGINNER_COMPLETED ||
                filter == FilterOption.Filter.ALL

        filterAdvanced.isChecked = filter == FilterOption.Filter.ADVANCED ||
                filter == FilterOption.Filter.ADVANCED_COMPLETED ||
                filter == FilterOption.Filter.BEGINNER_ADVANCED ||
                filter == FilterOption.Filter.ALL

        filterCompleted.isChecked = filter == FilterOption.Filter.COMPLETED ||
                filter == FilterOption.Filter.BEGINNER_COMPLETED ||
                filter == FilterOption.Filter.ADVANCED_COMPLETED ||
                filter == FilterOption.Filter.ALL
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.dayNightMode) {
            viewModel.toggleNightMode()
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (nightModeActive) {
            menu?.findItem(R.id.dayNightMode)?.setIcon(R.drawable.icn_night_mode)
        } else {
            menu?.findItem(R.id.dayNightMode)?.setIcon(R.drawable.icn_light_mode)
        }
        return true
    }
}