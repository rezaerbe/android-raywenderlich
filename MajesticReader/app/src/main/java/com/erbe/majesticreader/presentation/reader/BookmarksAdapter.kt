package com.erbe.majesticreader.presentation.reader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erbe.majesticreader.R
import com.erbe.core.domain.Bookmark
import kotlinx.android.synthetic.main.item_bookmark.view.*

class BookmarksAdapter(
    private val bookmarks: MutableList<Bookmark> = mutableListOf(),
    private val itemClickListener: (Bookmark) -> Unit
) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val titleTextView: TextView = view.bookmarkNameTextView
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false)
    )
  }

  override fun getItemCount() = bookmarks.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.titleTextView.text = holder.itemView.resources.getString(
        R.string.page_bookmark_format,
        bookmarks[position].page
    )
    holder.itemView.setOnClickListener { itemClickListener.invoke(bookmarks[position]) }
  }

  fun update(newBookmarks: List<Bookmark>) {
    bookmarks.clear()
    bookmarks.addAll(newBookmarks)

    notifyDataSetChanged()
  }
}