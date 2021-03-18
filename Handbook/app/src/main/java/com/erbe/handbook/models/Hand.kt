package com.erbe.handbook.models

import java.io.Serializable

data class Hand(
    val userName: String,
    val fingersCount: Int,
    val thumbsCount: Int,
    val bio: String,
    val password: String
) : Serializable