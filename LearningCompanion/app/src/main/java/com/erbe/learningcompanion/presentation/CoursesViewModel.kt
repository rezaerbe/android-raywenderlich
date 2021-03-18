package com.erbe.learningcompanion.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.erbe.learningcompanion.data.FilterOption
import com.erbe.learningcompanion.data.getCourseList
import com.erbe.learningcompanion.data.model.Course
import com.erbe.learningcompanion.data.model.CourseLevel
import com.erbe.learningcompanion.prefsstore.PrefsStore
import com.erbe.learningcompanion.protostore.ProtoStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val prefsStore: PrefsStore,
    private val protoStore: ProtoStore
) : ViewModel() {

    private val courseUiModelFlow = combine(
        getCourseList(),
        protoStore.filtersFlow
    ) { courses: List<Course>, filterOption: FilterOption ->
        return@combine CourseUiModel(
            courses = filterCourses(courses, filterOption),
            filter = filterOption.filter
        )
    }

    private fun filterCourses(courses: List<Course>, filterOption: FilterOption): List<Course> {
        return when (filterOption.filter) {
            FilterOption.Filter.BEGINNER -> courses.filter { it.level == CourseLevel.BEGINNER }
            FilterOption.Filter.NONE -> courses
            FilterOption.Filter.ADVANCED -> courses.filter { it.level == CourseLevel.ADVANCED }
            FilterOption.Filter.COMPLETED -> courses.filter { it.completed }
            FilterOption.Filter.BEGINNER_ADVANCED -> courses.filter {
                it.level == CourseLevel.BEGINNER || it.level == CourseLevel.ADVANCED
            }
            FilterOption.Filter.BEGINNER_COMPLETED -> courses.filter {
                it.level == CourseLevel.BEGINNER || it.completed
            }
            FilterOption.Filter.ADVANCED_COMPLETED -> courses.filter {
                it.level == CourseLevel.ADVANCED || it.completed
            }
            FilterOption.Filter.ALL -> courses
            // There shouldn't be any other value for filtering
            else -> throw UnsupportedOperationException("$filterOption doesn't exist.")
        }
    }

    val darkThemeEnabled = prefsStore.isNightMode().asLiveData()
    val courseUiModel = courseUiModelFlow.asLiveData()

    fun enableBeginnerFilter(enable: Boolean) {
        viewModelScope.launch {
            protoStore.enableBeginnerFilter(enable)
        }
    }

    fun enableAdvancedFilter(enable: Boolean) {
        viewModelScope.launch {
            protoStore.enableAdvancedFilter(enable)
        }
    }

    fun enableCompletedFilter(enable: Boolean) {
        viewModelScope.launch {
            protoStore.enableCompletedFilter(enable)
        }
    }

    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.toggleNightMode()
        }
    }
}

data class CourseUiModel(val courses: List<Course>, val filter: FilterOption.Filter)