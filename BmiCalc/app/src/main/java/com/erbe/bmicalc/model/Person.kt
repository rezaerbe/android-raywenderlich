package com.erbe.bmicalc.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Person(
    var height: Float,
    var birthdate: Date,
    val logs: MutableList<WeightLog> = mutableListOf()
) : Parcelable {

    fun bmi(log: WeightLog): Float = log.weight / (height * height)

    fun bmiState(log: WeightLog): BMIState {
        val bmi = bmi(log)
        if (bmi <= 18.5f) {
            return BMIState.Underweight
        }
        if (bmi <= 25.0f) {
            return BMIState.Healthy
        }
        if (bmi <= 30.0f) {
            return BMIState.Overweight
        }
        return BMIState.Obese
    }
}

enum class BMIState {
    Underweight,
    Healthy,
    Overweight,
    Obese
}