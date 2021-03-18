package com.erbe.droidwiki.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erbe.droidwiki.R
import com.erbe.droidwiki.model.Entry
import com.erbe.droidwiki.utils.parseHtml

class EntryAdapter(private val context: Context, private val results: List<Entry>) : RecyclerView.Adapter<EntryAdapter.EntryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder =
        EntryHolder(LayoutInflater.from(context).inflate(R.layout.wiki_result, parent, false))

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        holder?.let {
            it.titleView.text = results[position].title
            it.snippetView.text = results[position].snippet.parseHtml()
        }
    }

    override fun getItemCount() = results.size

    inner class EntryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.title_tv)
        val snippetView: TextView = view.findViewById(R.id.snippet_tv)
    }
}