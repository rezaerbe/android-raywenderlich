package com.erbe.foodeat.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Restaurant(
    @PrimaryKey
    var name: String,
    var rating: Int
)