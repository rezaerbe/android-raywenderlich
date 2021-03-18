package com.erbe.bmicalc.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class WeightLog(
    val weight: Float,
    val date: Date
) : Parcelable