package com.erbe.learningcompanion.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erbe.learningcompanion.data.model.Course

class CoursesAdapter : RecyclerView.Adapter<CourseViewHolder>() {

    private val courses = mutableListOf<Course>()

    fun setCourses(courses: List<Course>) {
        this.courses.clear()
        this.courses.addAll(courses)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CourseViewHolder.create(parent)

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size
}