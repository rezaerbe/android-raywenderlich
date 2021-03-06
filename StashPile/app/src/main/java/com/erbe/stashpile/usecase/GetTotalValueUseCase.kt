package com.erbe.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import kotlin.random.Random

private const val INITIAL_TOTAL_VALUE = 15_000.00
private const val TOTAL_VALUE_DIFF_RANGE = 5.00
private const val TOTAL_VALUE_INITIAL_DELAY_MS = 1_000L
private const val TOTAL_VALUE_UPDATE_RATE_MS = 2_000L

/**
 * Returns the total value of the user's stocks, and updates it every [TOTAL_VALUE_UPDATE_RATE_MS]
 * milliseconds, allowing it to be displayed in real time to the user.
 *
 * This class demonstrates exposing a [LiveData] generated with a [liveData] builder, which
 * internally runs forever until the scope within which the coroutine is running is terminated.
 */
class GetTotalValueUseCase {

    fun get(): LiveData<Double> = liveData {
        emit(0.00)
        delay(TOTAL_VALUE_INITIAL_DELAY_MS)
        emitSource(getTotalValue())
    }

    private fun getTotalValue(): LiveData<Double> = liveData {
        var total = INITIAL_TOTAL_VALUE
        while (true) {
            total = total.moreOrLessWithMargin(TOTAL_VALUE_DIFF_RANGE)
            emit(total)
            delay(TOTAL_VALUE_UPDATE_RATE_MS)
        }
    }

    private fun Double.moreOrLessWithMargin(margin: Double): Double {
        val diff = Random.nextDouble(-margin, margin)
        return this + diff
    }
}