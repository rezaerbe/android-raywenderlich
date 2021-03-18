package com.erbe.rwquotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.rwquotes.R
import com.erbe.rwquotes.data.Quote
import kotlinx.android.synthetic.main.rwquote_item.view.*

class QuoteAdapter() : RecyclerView.Adapter<QuoteViewHolder>() {

  private var quotes: List<Quote> = listOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.rwquote_item, parent, false)
    return QuoteViewHolder(view)
  }

  override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
    val quote = quotes[position]
    holder.bind(quote)
  }

  override fun getItemCount(): Int = quotes.size

  fun setQuotes(quotes: List<Quote>) {
    this.quotes = quotes
    notifyDataSetChanged()
  }
}

class QuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  fun bind(quote: Quote) {
    itemView.quoteText.text = quote.text
    itemView.quoteAuthor.text = quote.author
    itemView.quoteDate.text = quote.date
  }
}