package com.erbe.libcollection.collectiontypes

import com.erbe.libcollection.collectiontypes.util.Worker

fun main() {
    val mutableItems = mutableListOf<Worker>()

    // adding and removing item
    mutableItems.add(Worker(id = 5, name = "Filip")) // we can add item
    println(mutableItems)
    mutableItems.removeAt(0) // removing values
    println(mutableItems)

    // adding a list of item
    mutableItems.addAll(
        listOf(
            Worker(0, "John"),
            Worker(1, "Peter"),
            Worker(2, "Stephanie")
        )
    )

    println(mutableItems)

    // clearing all the items
    mutableItems.clear()
    println(mutableItems)

    var items = listOf("First", "Second", "Third")
    println(items)

    items = listOf("Fourth")
    println(items)

    val immutableItems = listOf("Immutable item")

    // cannot remove or add items
    val result = immutableItems - "Immutable item" // this works, since it's creating a new list
    println(immutableItems)
    println(result)
}