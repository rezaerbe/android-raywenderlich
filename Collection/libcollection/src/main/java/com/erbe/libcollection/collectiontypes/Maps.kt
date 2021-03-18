package com.erbe.libcollection.collectiontypes

fun main() {
    val httpHeaders = hashMapOf(
        "Authorization" to "your-api-key",
        "ContentType" to "application/json",
        "UserLocale" to "US"
    )

    httpHeaders.forEach { key, value -> println("Value for key $key is $value") } // printing formatted keys and values
    println(httpHeaders) // printing a map as an object

    httpHeaders["Authorization"] = "something else"
    println(httpHeaders)
}