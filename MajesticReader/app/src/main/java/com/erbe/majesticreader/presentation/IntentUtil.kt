package com.erbe.majesticreader.presentation

import android.content.Intent

object IntentUtil {

  fun createOpenIntent() = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
    addCategory(Intent.CATEGORY_OPENABLE)
    type = "application/pdf"
  }
}