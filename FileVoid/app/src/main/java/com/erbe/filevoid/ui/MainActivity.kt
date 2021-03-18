package com.erbe.filevoid.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.filevoid.R
import com.erbe.filevoid.adapter.FilePathRecyclerAdapter
import com.erbe.filevoid.data.FileInfo
import com.erbe.filevoid.data.FileType
import com.erbe.filevoid.util.FileStack
import com.erbe.filevoid.util.FileUpdateBroadcastReceiver
import com.erbe.filevoid.util.secureDeleteFile
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), FileBrowserFragment.OnItemClickListener {

    companion object {
        private const val OPTIONS_DIALOG_TAG = "com.erbe.filevoid.options_dialog"
    }

    private val fileStack = FileStack()
    private lateinit var filePathRecyclerAdapter: FilePathRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val filesListFragment = FileBrowserFragment.build {
                path = Environment.getExternalStorageDirectory().absolutePath
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.container, filesListFragment)
                .addToBackStack(Environment.getExternalStorageDirectory().absolutePath)
                .commit()
        }

        requestExternalStoragePermissions()
        setupUI()
    }

    fun setupUI() {
        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filePathRecyclerAdapter = FilePathRecyclerAdapter()
        recyclerView.adapter = filePathRecyclerAdapter
        filePathRecyclerAdapter.onItemClickListener = {
            supportFragmentManager.popBackStack(it.path, 2)
            fileStack.eraseTo(it)
        }

        fileStack.push(FileInfo(Environment.getExternalStorageDirectory().absolutePath,
            FileType.FOLDER,
            "/",
            0.0).apply { numberOfChildren = 0 })
    }

    override fun onClick(fileInfo: FileInfo) {
        if (fileInfo.fileType == FileType.FOLDER) {
            pushFileBrowserFragment(fileInfo)
        }
    }

    override fun onLongClick(fileInfo: FileInfo) {
        val optionsDialog = OptionsDialogFragment.build {}

        optionsDialog.onDeleteClickListener = {
            doAsync {
                secureDeleteFile(fileInfo.path)
                uiThread {
                    optionsDialog.dismiss()
                    refreshCurrentFragment()
                    coordinatorLayout.displaySnackbar("'${fileInfo.name}' deleted successfully.")
                }
            }
        }

        optionsDialog.show(supportFragmentManager, OPTIONS_DIALOG_TAG)
    }

    private fun pushFileBrowserFragment(fileInfo: FileInfo) {
        val filesListFragment = FileBrowserFragment.build {
            path = fileInfo.path
        }

        fileStack.push(fileInfo)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.right_forward_navigation,
            R.anim.left_back_navigation,
            R.anim.left_forward_navigation,
            R.anim.right_back_navigation)
        fragmentTransaction.replace(R.id.container, filesListFragment)
        fragmentTransaction.addToBackStack(fileInfo.path)
        fragmentTransaction.commit()
    }

    private fun refreshData(files: List<FileInfo>) {
        filePathRecyclerAdapter.refreshData(files)
        if (files.isNotEmpty()) {
            recyclerView.smoothScrollToPosition(files.size - 1)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        fileStack.pop()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    private fun requestExternalStoragePermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
        1)
    }

    private fun refreshCurrentFragment() {
        val broadcastIntent = Intent()
        broadcastIntent.action = applicationContext.getString(R.string.file_change_broadcast)
        broadcastIntent.putExtra(FileUpdateBroadcastReceiver.EXTRA_PATH, fileStack.top.path)
        sendBroadcast(broadcastIntent)
    }

    private fun View.displaySnackbar(message: String) {
        val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}