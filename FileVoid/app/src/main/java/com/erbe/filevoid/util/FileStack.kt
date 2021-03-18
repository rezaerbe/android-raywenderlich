package com.erbe.filevoid.util

import com.erbe.filevoid.data.FileInfo

class FileStack {

    private var files = mutableListOf<FileInfo>()
    var onStackChangeListener: ((List<FileInfo>) -> Unit)? = null

    val top: FileInfo
        get() = files[files.size - 1]

    fun push(fileInfo: FileInfo) {
        files.add(fileInfo)

        onStackChangeListener?.run {
            invoke(files)
            println("pushing, calling " + toString())
        }
    }

    fun pop() {
        if (files.isNotEmpty()) {
            files.removeAt(files.size - 1)
        }

        onStackChangeListener?.run {
            invoke(files)
            println("popping, calling " + toString())
        }
    }

    fun eraseTo(fileInfo: FileInfo) {
        files = files.subList(0, files.indexOf(fileInfo) + 1)

        onStackChangeListener?.run {
            invoke(files)
            println("erasing, calling " + toString())
        }
    }
}