package com.erbe.librwdc

import kotlinx.coroutines.*

fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope { // Creates a new coroutine scope
        val job = launch {
            println("Task from nested launch, this is printed")
            delay(500L)
            println("Task from nested launch, this won't be printed")
        }

        delay(100L)
        println("Task from first coroutine scope") // Printed before initial launch
        job.cancel() // This cancels nested launch's execution
    }

    println("Coroutine scope is over") // This is not printed until nested launch completes/is cancelled
}