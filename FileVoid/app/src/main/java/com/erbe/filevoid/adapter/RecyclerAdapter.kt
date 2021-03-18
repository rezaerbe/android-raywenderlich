package com.erbe.filevoid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.filevoid.R
import com.erbe.filevoid.data.FileInfo
import com.erbe.filevoid.data.FileType
import kotlinx.android.synthetic.main.adapter_recycler.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var onItemClickListener: ((FileInfo) -> Unit)? = null
    var onItemLongClickListener: ((FileInfo) -> Unit)? = null
    var fileInfoList = listOf<FileInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindView(position)

    override fun getItemCount(): Int = fileInfoList.size

    fun updateFileList(fileList: List<FileInfo>) {
        this.fileInfoList = fileList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener?.invoke(fileInfoList[adapterPosition])
        }

        override fun onLongClick(v: View?): Boolean {
            onItemLongClickListener?.invoke(fileInfoList[adapterPosition])
            return true
        }

        fun bindView(position: Int) {
            val fileModel = fileInfoList[position]
            itemView.nameTextView.text = fileModel.name

            if (fileModel.fileType == FileType.FOLDER) {
                itemView.folderTextView.visibility = View.VISIBLE
                itemView.totalSizeTextView.visibility = View.GONE
                itemView.folderTextView.text = itemView.resources.getQuantityString(R.plurals.number_of_files, fileModel.numberOfChildren, fileModel.numberOfChildren)
            } else {
                itemView.folderTextView.visibility = View.GONE
                itemView.totalSizeTextView.visibility = View.VISIBLE
                itemView.totalSizeTextView.text = itemView.resources.getString(R.string.file_size, fileModel.size)
            }
        }
    }
}