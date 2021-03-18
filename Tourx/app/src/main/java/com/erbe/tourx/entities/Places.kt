package com.erbe.tourx.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias Places = List<Place>

@Entity
class Place(
    @PrimaryKey(autoGenerate = false) val placeId: Int,
    val name: String,
    val planet: String
)