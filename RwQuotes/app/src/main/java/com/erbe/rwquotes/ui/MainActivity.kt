package com.erbe.rwquotes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.rwquotes.R
import com.erbe.rwquotes.data.Quote
import com.erbe.rwquotes.data.QuotesRepositoryImpl
import com.erbe.rwquotes.ui.viewmodel.QuoteViewModelFactory
import com.erbe.rwquotes.ui.viewmodel.QuotesViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  private lateinit var quotesViewModel: QuotesViewModel
  private lateinit var quoteAdapter: QuoteAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.Theme_RwQuotes)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    quotesViewModel = ViewModelProvider(
        this,
        QuoteViewModelFactory(QuotesRepositoryImpl(application = application), application)
    ).get(QuotesViewModel::class.java)


    quotesViewModel.dataLoading.observe(this, Observer { value ->
      value?.let { show ->
        loading_spinner.visibility = if (show) View.VISIBLE else View.GONE
      }
    })

    quotesViewModel.getAllQuotes().observe(this, Observer<List<Quote>> {
      quoteAdapter.setQuotes(it)
    })

    quoteAdapter = QuoteAdapter()

    quotesRecyclerView.apply {
      layoutManager = LinearLayoutManager(applicationContext)
      setHasFixedSize(true)
      adapter = quoteAdapter
    }

    addQuoteFloatingButton.setOnClickListener {
      val intent = Intent(this, AddEditActivity::class.java)
      startActivityForResult(intent, ADD_QUOTE_REQUEST_CODE)
    }

  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      ADD_QUOTE_REQUEST_CODE -> {
        val intentData = data!!
        val newNote = Quote(
            text = intentData.getStringExtra(AddEditActivity.EXTRA_TEXT)!!,
            author = intentData.getStringExtra(AddEditActivity.EXTRA_AUTHOR)!!,
            date = intentData.getStringExtra(AddEditActivity.EXTRA_DATE)!!
        )
        quotesViewModel.insertQuote(newNote)
        Toast.makeText(this, "Quote saved!", Toast.LENGTH_SHORT).show()
      }
      EDIT_QUOTE_REQUEST_CODE -> {
        val intentData = data!!
        val updateQuote = Quote(
            text = intentData.getStringExtra(AddEditActivity.EXTRA_TEXT)!!,
            author = intentData.getStringExtra(AddEditActivity.EXTRA_AUTHOR)!!,
            date = intentData.getStringExtra(AddEditActivity.EXTRA_DATE)!!
        )
        quotesViewModel.updateQuote(updateQuote)
        Toast.makeText(this, "Quote updated!", Toast.LENGTH_SHORT).show()
      }
      else -> {
        Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  companion object {
    const val ADD_QUOTE_REQUEST_CODE = 1
    const val EDIT_QUOTE_REQUEST_CODE = 2
  }
}
