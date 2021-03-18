package com.erbe.bmicalc.model

import android.content.SharedPreferences
import com.google.gson.Gson

private const val PERSON = "PERSON"

class Repository(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson = Gson()
) {

    fun savePerson(person: Person) {
        val personJson = gson.toJson(person)
        sharedPreferences.edit().putString(PERSON, personJson).apply()
    }

    fun getPerson(): Person? {
        val personString = sharedPreferences.getString(PERSON, null)
        personString?.let {
            return gson.fromJson(personString, Person::class.java)
        } ?: run {
            return null
        }
    }
}