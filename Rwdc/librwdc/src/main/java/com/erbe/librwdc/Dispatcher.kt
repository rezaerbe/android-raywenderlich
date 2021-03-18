package com.erbe.librwdc

import kotlinx.coroutines.*

@ObsoleteCoroutinesApi
fun main() = runBlocking<Unit> {
    launch { //context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { //not confined -- will inmediatly run in main thread but not after suspension
        println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
        delay(100L) // delays (suspends) execution 100 ms
        println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { //will get dispatched to DefaultDispatcher
        println("Default: I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) {// will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}