package com.erbe.bmicalc.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erbe.bmicalc.model.Person
import com.erbe.bmicalc.model.Repository
import com.erbe.bmicalc.util.SingleLiveEvent
import com.erbe.bmicalc.util.toDateOrToday
import com.erbe.bmicalc.util.toFloatOrZero
import java.util.*

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> = _person

    val _isSaved = SingleLiveEvent<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    fun loadProfile() {
        _person.value = repository.getPerson()
    }

    fun saveProfile(birthdate: String, height: String) {

        var person = _person.value

        if (validateInputs(birthdate, height)) {
            if (person == null) {
                person = Person(height.toFloat(), birthdate.toDateOrToday())
            } else {
                person.birthdate = birthdate.toDateOrToday()
                person.height = height.toFloat()
            }
            repository.savePerson(person)
            _isSaved.value = true
        } else {
            _isSaved.value = false
        }
    }

    private fun validateInputs(birthdate: String, height: String): Boolean {
        val maxDate = Calendar.getInstance().apply { add(Calendar.YEAR, -18) }.time
        val minHeight = 0f
        return birthdate.toDateOrToday() <= maxDate && height.toFloatOrZero() > minHeight
    }
}