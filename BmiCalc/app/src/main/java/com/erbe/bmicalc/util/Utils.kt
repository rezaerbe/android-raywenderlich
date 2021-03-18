package com.erbe.bmicalc.util

import android.app.DatePickerDialog
import android.widget.EditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun String.toDateOrToday(): Date =
    try {
        df.parse(this) ?: Date()
    } catch (e: ParseException) {
        Date()
    }

fun String.toCalendar(): Calendar = Calendar.getInstance().apply {
    time = toDateOrToday()
}

fun Date.toFormattedString(): String = df.format(this)

fun Calendar.toFormattedString(): String = df.format(time)

fun showDatePickerDialog(editText: EditText) {
    val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        editText.setText(cal.toFormattedString())
    }

    val cal = editText.text.toString().toCalendar()
    DatePickerDialog(
        editText.context,
        listener,
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun String.toFloatOrZero(): Float {
    return toFloatOrNull() ?: 0f
}

fun Float.toFormattedString() = "%.2f".format(Locale.US, this)