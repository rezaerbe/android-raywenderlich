package com.erbe.filevoid.legacy

import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.security.SecureRandom
import java.util.*

object FileShredder {

    const val SHRED_MODE_ZERO = "zero"
    const val SHRED_MODE_RANDOM = "random"
    const val SHRED_MODE_3PASS = "3-pass"

    private fun stringForMode(mode: Int): String? {
        return if (mode == 1) {
            SHRED_MODE_ZERO
        } else if (mode == 2) {
            SHRED_MODE_RANDOM
        } else if (mode == 3) {
            SHRED_MODE_3PASS
        } else {
            null
        }
    }

    fun operationsForMode(mode: Int): List<String?> {
        return if (mode == 3) {
            val list: MutableList<String?> = ArrayList()
            list.add(stringForMode(2))
            list.add(stringForMode(2))
            list.add(stringForMode(1))
            list
        } else {
            val list: MutableList<String?> = ArrayList()
            list.add(stringForMode(mode))
            list
        }
    }

    @Throws(IOException::class)
    fun secureWipeFile(file: File?): Boolean {
        var success = false
        if (file != null && file.exists()) {
            val length = file.length()
            val random = SecureRandom()
            val randomAccessFile = RandomAccessFile(file, "rws")
            randomAccessFile.seek(0)
            randomAccessFile.filePointer
            val data = ByteArray(64)
            var position = 0
            while (position < length) {
                random.nextBytes(data)
                randomAccessFile.write(data)
                position += data.size
            }
            randomAccessFile.close()
            success = file.delete()
        }
        return success
    }
}