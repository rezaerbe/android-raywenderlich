package com.erbe.learningcompanion.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.erbe.learningcompanion.R
import com.erbe.learningcompanion.data.model.Course
import com.erbe.learningcompanion.data.model.CourseLevel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.course_list_item.view.*

class CourseViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(course: Course) = with(containerView) {
        courseName.text = course.name
        courseDescription.text = course.description
        val completedResource = if (course.completed) {
            ContextCompat.getDrawable(context, R.drawable.icn_checkmark_filled)
        } else {
            ContextCompat.getDrawable(context, R.drawable.icn_checkmark_outlined)
        }
        courseCompleted.setImageDrawable(completedResource)
        when (course.level) {
            CourseLevel.BEGINNER -> levelIndicator.setImageResource(R.drawable.shape_level_beginner)
            CourseLevel.ADVANCED -> levelIndicator.setImageResource(R.drawable.shape_level_advanced)
        }
    }

    companion object {
        fun create(parent: ViewGroup): CourseViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.course_list_item, parent, false)
            return CourseViewHolder(view)
        }
    }
}