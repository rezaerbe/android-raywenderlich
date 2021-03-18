package com.erbe.markme.utils

import android.os.Parcelable
import com.erbe.markme.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ClassSection(val sectionName: String, val color: Int) : Parcelable {
    ATTENDANCE("Attendance", R.color.attendanceColor),
    GRADING("Grading", R.color.gradingColor)
}