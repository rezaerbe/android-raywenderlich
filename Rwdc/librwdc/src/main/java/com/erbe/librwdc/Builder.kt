package com.erbe.librwdc

import kotlinx.coroutines.*
import java.lang.Thread

fun main() {
    GlobalScope.launch {  // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
        val sum1 = async { // non blocking sum1
            delay(100L)
            2 + 2
        }
        val sum2 = async { // non blocking sum2
            delay(500L)
            3 + 3
        }
        println("waiting concurrent sums")
        val total = sum1.await() + sum2.await() // execution stops until both sums are calculated
        println("Total is: $total")
    }
    println("Hello,")     // main thread continues while coroutine executes
    Thread.sleep(2000L)   // block main thread for 2 seconds to keep JVM alive
}