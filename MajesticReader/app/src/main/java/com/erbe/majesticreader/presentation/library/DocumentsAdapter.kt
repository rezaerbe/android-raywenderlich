package com.erbe.majesticreader.presentation.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.erbe.majesticreader.R
import com.erbe.core.domain.Document
import com.erbe.majesticreader.presentation.StringUtil
import kotlinx.android.synthetic.main.item_document.view.*

class DocumentsAdapter(
    private val documents: MutableList<Document> = mutableListOf(),
    private val glide: RequestManager,
    private val itemClickListener: (Document) -> Unit
) : RecyclerView.Adapter<DocumentsAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val previewImageView: ImageView = view.ivPreview
    val titleTextView: TextView = view.tvTitle
    val sizeTextView: TextView = view.tvSize
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_document, parent, false)
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.run {
    glide.load(documents[position].thumbnail)
        .error(glide.load(R.drawable.preview_missing))
        .into(holder.previewImageView)
    holder.previewImageView.setImageResource(R.drawable.preview_missing)
    holder.titleTextView.text = documents[position].name
    holder.sizeTextView.text = StringUtil.readableFileSize(documents[position].size)
    holder.itemView.setOnClickListener { itemClickListener.invoke(documents[position]) }
  }

    override fun getItemCount() = documents.size

  fun update(newDocuments: List<Document>) {
    documents.clear()
    documents.addAll(newDocuments)

    notifyDataSetChanged()
  }
}