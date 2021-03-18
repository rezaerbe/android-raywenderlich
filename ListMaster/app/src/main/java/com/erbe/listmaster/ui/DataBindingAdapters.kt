package com.erbe.listmaster.ui

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("android:highlight_tint")
    fun setHighlightTint(textView: TextView, colorResourceId: Int) {
        Log.i("color filter", colorResourceId.toString())
        textView.background.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(textView.context,
            colorResourceId), PorterDuff.Mode.SRC)
    }
}