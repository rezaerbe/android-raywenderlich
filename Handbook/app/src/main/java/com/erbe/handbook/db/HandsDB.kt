package com.erbe.handbook.db

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.erbe.handbook.models.Hand
import com.google.gson.Gson

class HandsDB(context: Context) {

    private val gson = Gson()
    private val preferences = context
            .getSharedPreferences("hands_db", MODE_PRIVATE)

    companion object {
        private const val LOGGED_IN_HAND_KEY = "logged_in_hand"
    }

    fun getLoggedInHand(): Hand? {
        val handString = preferences.getString(LOGGED_IN_HAND_KEY, null) ?: return null
        return gson.fromJson(handString, Hand::class.java)
    }

    private fun checkForExistingUsername(username: String): Boolean {
        val hand = preferences.getString("hand_${username}", null)
        return hand != null
    }

    fun performLogin(username: String, password: String): LoginState {
        val handString = preferences.getString("hand_${username}", null)
                ?: return LoginState.HAND_NOT_REGISTERED

        val hand = gson.fromJson(handString, Hand::class.java)
        return if (hand.password == password) {
            val editor = preferences.edit()
            editor.putString(LOGGED_IN_HAND_KEY, handString)
            editor.apply()
            LoginState.SUCCESS
        } else {
            LoginState.WRONG_PASSWORD
        }
    }

    fun registerHand(hand: Hand): RegistrationState {
        if (checkForExistingUsername(hand.userName)) {
            return RegistrationState.HAND_ALREADY_EXISTS
        }

        val handString = gson.toJson(hand)
        val editor = preferences.edit()
        editor.putString("hand_${hand.userName}", handString)
        editor.putString(LOGGED_IN_HAND_KEY, handString)
        editor.apply()

        return RegistrationState.SUCCESS
    }

    fun logoutHand(): LogoutState {
        getLoggedInHand() ?: return LogoutState.NOT_LOGGED_IN

        val editor = preferences.edit()
        editor.remove(LOGGED_IN_HAND_KEY)
        editor.apply()

        return LogoutState.SUCCESS
    }
}