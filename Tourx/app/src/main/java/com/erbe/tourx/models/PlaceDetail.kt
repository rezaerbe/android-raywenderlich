package com.erbe.tourx.models

import com.erbe.tourx.entities.Cost
import com.erbe.tourx.entities.Place

data class PlaceDetail(
    val place: Place,
    val cost: Cost
)