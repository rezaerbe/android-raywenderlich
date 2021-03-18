package com.erbe.tourx.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cost(
    @PrimaryKey(autoGenerate = false)
    val placeId: Int,
    val price: Double
)