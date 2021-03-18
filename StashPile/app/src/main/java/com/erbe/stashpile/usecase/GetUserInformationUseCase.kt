package com.erbe.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

private const val GET_USER_INFORMATION_DELAY = 1_000L
private const val USER_NAME = "John Doe"
private const val ACCOUNT_NUMBER = "ABCDEFG_12345"
private const val PHONE_NUMBER = "(123) 456-7890"

/**
 * Returns the user's information, including their name, account number and phone number. The
 * information is returned after [GET_USER_INFORMATION_DELAY] milliseconds, simulating the time
 * a network request would take in reality to fetch this information from a server.
 *
 * This class demonstrates exposing [LiveData] generated with a [liveData] builder.
 *
 * Inside a [liveData] builder, An item is emitted through this [LiveData] instance using the
 * [LiveDataScope.emit] method.
 *
 * The [delay] method allows to delay/pause the execution of the coroutine without blocking the
 * thread it's running on.
 */
class GetUserInformationUseCase {

    fun get(): LiveData<UserInformation> = liveData {
        delay(GET_USER_INFORMATION_DELAY)
        emit(UserInformation(USER_NAME, ACCOUNT_NUMBER, PHONE_NUMBER))
    }
}

data class UserInformation(val name: String, val accountNumber: String, val phoneNumber: String)