package com.erbe.snowy.utils

import android.graphics.Bitmap
import android.graphics.Color
import java.util.*

object SnowFilter {
    var COLOR_MAX = 0xff

    fun applySnowEffect(source: Bitmap): Bitmap {
        // get image size
        val width = source.width
        val height = source.height
        val pixels = IntArray(width * height)
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height)
        // random object
        val random = Random()

        var R: Int
        var G: Int
        var B: Int
        var index: Int
        var threshHold: Int
        // iteration through pixels
        for (y in 0 until height) {
            for (x in 0 until width) {
                // get current index in 2D-matrix
                index = y * width + x
                // get color
                R = Color.red(pixels[index])
                G = Color.green(pixels[index])
                B = Color.blue(pixels[index])
                // generate threshold
                threshHold = random.nextInt(COLOR_MAX)
                if (R > threshHold && G > threshHold && B > threshHold) {
                    pixels[index] = Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX)
                }
            }
        }
        // output bitmap
        val bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmapOut
    }
}