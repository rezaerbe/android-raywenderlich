package com.erbe.libcollection.collectiontypes

fun main() {
    // simple initialization
    val arrayOfNumbers = arrayOf(2, 3, 5, 6, 10)
    val intArray = intArrayOf(2, 3, 5, 10)

    val someArray = emptyArray<String>() // same as arrayOfString<String>()
    val someOtherArray = Array(5) { "" } // allocation with size and initializer

    println("The array size is: ${someArray.size}")
    println("The other array size is: ${someOtherArray.size}")

    // the difference in array types
    println(arrayOfNumbers)
    println(intArray)

    // accessing values

    // printing each value -> iterating
    arrayOfNumbers.forEach { println(it) }
    intArray.forEach { println(it) }

    // indexed iteration
    arrayOfNumbers.forEachIndexed { index, value ->
        println("Value at index $index is $value")
    }

    // Iterating using for loops
    for (number in arrayOfNumbers) {
        println(number)
    }

    for (number in intArray) {
        println(number)
    }

    // using indices
    val thirdValue = arrayOfNumbers[2] // same as arrayOfNumbers.get(2)
    println("The third value is $thirdValue")
    arrayOfNumbers[2] = 100 // we can reassign the values

    // helpful function
    println("The last value is: ${arrayOfNumbers.last()} and the first is: ${arrayOfNumbers.first()}")

    val names = arrayOf("Filip", "Babic", "Kotlin", "RayWenderlich")
    printValues("First", "Second", "Third", "Fourth")
    printValues(*names) // spread operator
}

fun printValues(vararg values: String) {
    values.forEach { println(it) }
}