package com.erbe.filevoid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.filevoid.R
import com.erbe.filevoid.data.FileInfo
import kotlinx.android.synthetic.main.adapter_file_path_recycler.view.*

class FilePathRecyclerAdapter : RecyclerView.Adapter<FilePathRecyclerAdapter.ViewHolder>() {

    var onItemClickListener: ((FileInfo) -> Unit)? = null

    var files = listOf<FileInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_file_path_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindView(position)

    override fun getItemCount(): Int = files.size

    fun refreshData(files: List<FileInfo>) {
        this.files = files
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener?.invoke(files[adapterPosition])
        }

        fun bindView(position: Int) {
            val file = files[position]
            itemView.nameTextView.text = file.name
        }
    }
}