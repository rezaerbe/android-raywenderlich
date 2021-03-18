package com.erbe.filevoid.data

import java.io.File

enum class FileType {
    FILE,
    FOLDER;

    companion object {
        fun fileType(file: File) = when (file.isDirectory) {
            true -> FOLDER
            false -> FILE
        }
    }
}

data class FileInfo (
        val path: String,
        val fileType: FileType,
        val name: String,
        val size: Double,
        val extension: String = "",
        var numberOfChildren: Int = 0
)