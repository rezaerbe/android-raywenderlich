package com.erbe.filevoid.util

import com.erbe.filevoid.data.FileInfo
import com.erbe.filevoid.data.FileType
import com.erbe.filevoid.legacy.FileShredder.SHRED_MODE_RANDOM
import com.erbe.filevoid.legacy.FileShredder.operationsForMode
import java.io.File
import java.io.RandomAccessFile
import java.security.SecureRandom
import java.util.*

fun fileInfoListFromFileList(files: List<File>): List<FileInfo> {

    return files.map {
        FileInfo(
            it.path,
            FileType.fileType(it),
            it.name,
            mbFromBytes(it.length()),
            it.extension,
            it.listFiles()?.size ?: 0).also { theFileInfo ->
                requireNotNull(theFileInfo.path)
                requireNotNull(theFileInfo.fileType)
                requireNotNull(theFileInfo.name)
                requireNotNull(theFileInfo.size)
                requireNotNull(theFileInfo.extension)
            }
    }
}

fun fileList(
        path: String,
        showHiddenFile: Boolean = false,
        onlyFolders: Boolean = false
): List<File> {

    val file = File(path)

    val list = file.listFiles()
            ?.filter { showHiddenFile || !it.name.startsWith(".") }
            ?.filter { !onlyFolders || it.isDirectory }
            ?.toList()

    return list ?: listOf()
}

fun mbFromBytes(sizeInBytes: Long): Double {
    return (sizeInBytes.toDouble()) / (1024 * 1024)
}

private fun wipeFile(file: File, operations: List<String?>) {

    if (file.exists()) {

        val validOperations = operations.filterNotNull()
        val iterator = validOperations.listIterator()
        for (item in iterator) {
            print("Wipe file with mode: " + item.toUpperCase(Locale.ROOT))
            val length = file.length()

            var random: SecureRandom? = null
            if (item == SHRED_MODE_RANDOM) {
                random = SecureRandom()
            }

            val randomAccessFile = RandomAccessFile(file, "rws")
            randomAccessFile.seek(0)
            randomAccessFile.filePointer
            val data = ByteArray(64)
            var position = 0
            while (position < length) {
                random?.nextBytes(data)
                randomAccessFile.write(data)
                position += data.size
            }
            randomAccessFile.close()
        }
        file.delete()
    }
}

fun secureDeleteFile(path: String) {
    File(path).walkBottomUp().forEach {

        if (it.isDirectory) {
            val operations = operationsForMode(0)
            wipeFile(it, operations)
        } else {
            val operations = operationsForMode(3)
            wipeFile(it, operations)
        }
    }
}