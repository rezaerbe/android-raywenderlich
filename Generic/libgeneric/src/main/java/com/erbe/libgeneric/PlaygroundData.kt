package com.erbe.libgeneric

import sun.management.counter.Units

open class Animal(open val id: Int, open val name: String)

data class Dog(override val id: Int, override val name: String, val furColor: FurColor) : Animal(id, name)

data class Cat(override val id: Int, override val name: String, val eyesColor: EyesColor) : Animal(id, name)

abstract class Bird(override val id: Int, override val name: String, open val featherColor: FeatherColor) : Animal(id, name)

data class Canary(
    override val id: Int,
    override val name: String,
    override val featherColor: FeatherColor,
    val tweetPitch: Int
) : Bird(id, name, featherColor)

data class Eagle(
    override val id: Int,
    override val name: String,
    override val featherColor: FeatherColor,
    val maxSpeed: Float
) : Bird(id, name, featherColor)

data class Penguin(
    override val id: Int,
    override val name: String,
    override val featherColor: FeatherColor,
    val age: Int
) : Bird(id, name, featherColor)

enum class FurColor {
    WHITE, BLACK, BROWN, SPOTTED, PATCHED
}

enum class EyesColor {
    BLUE, GREEN, BROWN, YELLOWISH
}

enum class FeatherColor {
    YELLOW, RED, GREEN, WHITE
}

class Cage<out T : Animal>(val animal: T, val size: Double) {
//    fun sampleFun(t: T) {
//        println("dummy behavior")
//    }
}

class InvariantCage<T : Animal>(private val t: T?) {

    fun getId(): Int? = t?.id

    fun getName(): String? = t?.name

    fun getContentType(): T? = t?.let { t } ?: run { null }

    fun printAnimalInfo(): String = "Animal ${t?.id} is called ${t?.name}"
}

class CovariantCage<out T : Animal>(private val t: T?) {

    fun getId(): Int? = t?.id

    fun getName(): String? = t?.name

    fun getContentType(): T? = t?.let { t } ?: run { null }

    fun printAnimalInfo(): String = "Animal ${t?.id} is called ${t?.name}"
}

class ContravariantCage<in T : Bird>(private var t: T?) {

    fun getId(): Int? = t?.id

    fun getName(): String? = t?.name

    fun setContentType(t: T) {
        this.t = t
    }

    fun printAnimalInfo(): String = "Animal ${t?.id} is called ${t?.name}"
}

interface Repository<S: Cage<Animal>> {

    fun registerCage(cage: S): Unit
}

class AnimalRepository : Repository<Cage<Animal>> {

    override fun registerCage(cage: Cage<Animal>) {
        println("registering cage for: ${cage.animal.name}")
    }
}

class BirdRepository: Repository<Cage<Bird>> {

    override fun registerCage(cage: Cage<Bird>) {
        println("registering cage for: ${cage.animal.name}")
    }
}