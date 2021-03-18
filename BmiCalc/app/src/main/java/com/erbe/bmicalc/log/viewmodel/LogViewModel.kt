package com.erbe.bmicalc.log.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erbe.bmicalc.model.Person
import com.erbe.bmicalc.model.Repository
import com.erbe.bmicalc.model.WeightLog
import com.erbe.bmicalc.util.toDateOrToday
import com.erbe.bmicalc.util.toFloatOrZero
import com.erbe.bmicalc.util.toFormattedString
import java.util.*

class LogViewModel(
    private val repository: Repository,
    private val person: Person
) : ViewModel() {

    val weight = MutableLiveData<String>()
    val date = MutableLiveData<String>().apply { value = Date().toFormattedString() }

    val isSaveEnabled = MediatorLiveData<Boolean>().apply {
        addSource(weight) { value = validateInputs() }
        addSource(date) { value = validateInputs() }
    }

    private fun validateInputs(): Boolean {
        weight.value?.let { w ->
            date.value?.let { d ->
                return w.toFloatOrZero() > 0f && d.toDateOrToday() <= Date()
            }
        }

        return false
    }

    fun saveLog() {
        weight.value?.let { w ->
            date.value?.let { d ->
                person.logs.add(WeightLog(w.toFloat(), d.toDateOrToday()))
                repository.savePerson(person)
            }
        }
    }
}