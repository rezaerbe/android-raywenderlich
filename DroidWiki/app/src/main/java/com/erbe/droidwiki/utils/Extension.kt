package com.erbe.droidwiki.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import kotlin.reflect.KClass

fun @receiver:StringRes Int.errorDialog(activity: Activity) {
    AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert)
        .setTitle("Error")
        .setMessage(this@errorDialog)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        .setIcon(android.R.drawable.ic_dialog_alert).show()
}

fun String?.parseHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("deprecation")
        return Html.fromHtml(this)
    }
}

fun <T : Activity> KClass<T>.start(activity: Activity, finish: Boolean = false) {
    Intent(activity, this.java).apply {
        activity.startActivity(this)
    }
    if (finish) {
        activity.finish()
    }
}