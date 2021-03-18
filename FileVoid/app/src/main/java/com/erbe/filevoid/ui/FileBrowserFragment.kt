package com.erbe.filevoid.ui

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.filevoid.R
import com.erbe.filevoid.adapter.RecyclerAdapter
import com.erbe.filevoid.data.FileInfo
import com.erbe.filevoid.util.FileUpdateBroadcastReceiver
import com.erbe.filevoid.util.fileInfoListFromFileList
import com.erbe.filevoid.util.fileList
import kotlinx.android.synthetic.main.fragment_file_browser.*

class FileBrowserFragment : Fragment() {

    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var storedPath: String
    private lateinit var fileUpdateBroadcastReceiver: FileUpdateBroadcastReceiver

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(fileInfo: FileInfo)
        fun onLongClick(fileInfo: FileInfo)
    }

    companion object {
        private const val ARGUMENTS_PATH = "com.erbe.filevoid.fileslist.path"
        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var path = ""

        fun build(): FileBrowserFragment {
            val fragment = FileBrowserFragment()
            val args = Bundle()
            args.putString(ARGUMENTS_PATH, path)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onItemClickListener = context as? OnItemClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filePath = arguments?.getString(ARGUMENTS_PATH)

        filePath?.let {
            storedPath = it

            fileUpdateBroadcastReceiver = FileUpdateBroadcastReceiver(it) {
                refreshView()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_browser, container, false)
    }

    override fun onResume() {
        super.onResume()
        context?.registerReceiver(fileUpdateBroadcastReceiver,
            IntentFilter(getString(R.string.file_change_broadcast)))
    }

    override fun onPause() {
        super.onPause()
        context?.unregisterReceiver(fileUpdateBroadcastReceiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        filesRecyclerView.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = RecyclerAdapter()
        filesRecyclerView.adapter = recyclerAdapter

        recyclerAdapter.onItemClickListener = {
            onItemClickListener?.onClick(it)
        }

        recyclerAdapter.onItemLongClickListener = {
            onItemClickListener?.onLongClick(it)
        }

        refreshView()
    }

    private fun refreshView() {
        val files = fileInfoListFromFileList(fileList(storedPath))

        if (files.isEmpty()) {
            emptyFolderLayout.visibility = View.VISIBLE
        } else {
            emptyFolderLayout.visibility = View.INVISIBLE
        }

        recyclerAdapter.updateFileList(files)
    }
}