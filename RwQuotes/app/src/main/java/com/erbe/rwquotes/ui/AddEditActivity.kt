package com.erbe.rwquotes.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erbe.rwquotes.R
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.android.synthetic.main.rwquote_item.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddEditActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_edit)

    if (intent.hasExtra(EXTRA_ID)) {
      title = "Edit quote"
      quoteText.text = intent.getStringExtra(EXTRA_TEXT)
      quoteAuthor.text = intent.getStringExtra(EXTRA_AUTHOR)
      quoteDate.text = intent.getStringExtra(EXTRA_DATE)
    } else {
      title = "Add new quote"
    }
    buttonSaveQuote.setOnClickListener {
      saveQuote()
    }

    initUI()
  }

  private fun initUI() {
    val calendar = Calendar.getInstance();
    val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
      calendar.set(Calendar.YEAR, year)
      calendar.set(Calendar.MONTH, monthOfYear)
      calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
      val dateFormat = "dd/MM/yyyy"
      val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
      editTextQuoteDate.setText(simpleDateFormat.format(calendar.time))
    }

    editTextQuoteDate.setOnClickListener {
      DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
          calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
  }


  private fun saveQuote() {
    if (editTextQuoteText.text.toString().trim().isBlank() ||
        editTextQuoteAuthor.text.toString().trim().isBlank() ||
        editTextQuoteDate.text.toString().trim().isBlank()) {
      Toast.makeText(this, "Quote is empty! Please fill the missing fields", Toast.LENGTH_SHORT)
          .show()
      return
    }

    val data = Intent().apply {
      putExtra(EXTRA_TEXT, editTextQuoteText.text.toString())
      putExtra(EXTRA_AUTHOR, editTextQuoteAuthor.text.toString())
      putExtra(EXTRA_DATE, editTextQuoteDate.text.toString())
      if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
        putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
      }
    }

    setResult(Activity.RESULT_OK, data)
    finish()
  }

  @Throws(ParseException::class)
  fun formatDate(dateStr: String): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = formatter.parse(dateStr)
    return date!!.toString()
  }

  companion object {
    const val EXTRA_ID = "QUOTE_ID"
    const val EXTRA_TEXT = "QUOTE_TEXT"
    const val EXTRA_AUTHOR = "QUOTE_AUTHOR"
    const val EXTRA_DATE = "QUOTE_DATE"
  }
}