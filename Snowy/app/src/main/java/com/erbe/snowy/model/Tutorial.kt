package com.erbe.snowy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tutorial(
    val name: String,
    val url: String,
    val description: String) : Parcelable