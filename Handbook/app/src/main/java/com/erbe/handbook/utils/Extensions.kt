package com.erbe.handbook.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.erbe.handbook.R
import com.erbe.handbook.models.Hand
import com.erbe.handbook.ui.BaseActivity
import com.erbe.handbook.ui.OnBoardingActivity
import java.util.regex.Pattern

//DONE: 1 Add an extension function with ImageView as receiver to load image using glide
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}

//DONE: 3 Add extension function for BaseActivity to show greeting message
fun BaseActivity.greet() {
    Toast.makeText(this, getString(R.string.welcome_base_activity), Toast.LENGTH_SHORT).show()
}

//DONE: 4 Add extension function for OnBoardingActivity to show greeting message
fun OnBoardingActivity.greet() {
    Toast.makeText(this, getString(R.string.welcome_onboarding_activity), Toast.LENGTH_SHORT).show()
}

//DONE: 8 Add startActivityAndClearStack function below
fun Context.startActivityAndClearStack(clazz: Class<*>, extras: Bundle?) {
    val intent = Intent(this, clazz)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    if (extras != null) {
        intent.putExtras(extras)
    }
    startActivity(intent)
}

//DONE: 11 Add extension function to validate and suggest alternate usernames
fun EditText.validateUsername(): Boolean {
    val username = text.toString()

    val pattern = Pattern.compile("^[a-zA-Z]+[0-9]+$")
    val matcher = pattern.matcher(username)

    val isValid = matcher.matches()

    if (!isValid) {
        error = context.getString(R.string.username_validation_error, username)
    }

    return isValid
}

//DONE: 13 Add extension property to get total fingers
val Hand?.totalFingers: String
    get() {
        // return (fingersCount + thumbsCount).toString()
        if (this == null) return "-"
        return (fingersCount + thumbsCount).toString()
    }