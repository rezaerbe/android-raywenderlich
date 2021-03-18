package com.erbe.character.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Parcelable