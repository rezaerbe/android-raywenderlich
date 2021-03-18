package com.erbe.libsunzoid

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

suspend fun getValues(): Sequence<Int> = sequence {
    Thread.sleep(250)
    yield(1)
    Thread.sleep(250)
    yield(2)
    Thread.sleep(250)
    yield(3)
}

fun processValue() {
    runBlocking {
        val values = getValues()
        for (value in values) {
            println(value)
        }
    }
}

val namesFlow = flow {
    val names = listOf("Jody", "Steve", "Lance", "Joe")
    for (name in names) {
        delay(100)
        emit(name)
    }
}

fun main() = runBlocking {
    namesFlow
        .map { name -> name.length }
        .filter { length -> length < 5 }
        .collect { println(it) }

    println()

    processValue()
}