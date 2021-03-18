package com.erbe.bmicalc.main.model

import com.erbe.bmicalc.model.Person

sealed class MainUIModel {
    object MissingProfile : MainUIModel()
    data class ExistingProfile(val person: Person) : MainUIModel()
}