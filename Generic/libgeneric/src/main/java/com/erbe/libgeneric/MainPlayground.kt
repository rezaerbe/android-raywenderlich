package com.erbe.libgeneric

fun main() {

    val dog1: Animal = Dog(id = 0, name = "Swift", furColor = FurColor.BLACK)
    val dog2: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    var cat1: Animal = Cat(id = 3, name = "Tuno", eyesColor = EyesColor.GREEN)
    var cat2: Cat = Cat(id = 2, name = "Tula", eyesColor = EyesColor.YELLOWISH)
    var animal: Animal = Animal(id = 9, name = "Hurley")
    var animal1: Animal = Dog(id = 0, name = "Swift", furColor = FurColor.BLACK)
    var animal2: Animal = Cat(id = 3, name = "Tuno", eyesColor = EyesColor.GREEN)
    var animal3: Animal = Canary(id = 7, name = "Piti", featherColor = FeatherColor.YELLOW, tweetPitch = 3)
    var bird1: Bird = Eagle(id = 7, name = "Piti", featherColor = FeatherColor.YELLOW, maxSpeed = 75.0f)

    var a1: Animal
    var d1: Dog = Dog(id = 1, name = "Stu", furColor = FurColor.PATCHED)
    var a2: Animal = Cat(id = 3, name = "Tuno", eyesColor = EyesColor.GREEN)

    a1 = d1
    a2 = a1

    // inheritance and polymorphism allow us to do the following sort of re-assignation
    cat1 = dog2
    // obviously, the reverse does not apply
    // cat2 = dog1

    // --- parametrized classes ---
    var cageA: InvariantCage<Dog> = InvariantCage(dog2)
    var cageB: InvariantCage<Animal> = InvariantCage(dog2)
    // var cageC: InvariantCage<Animal> = cageA

    var cage1: CovariantCage<Dog> = CovariantCage(dog2)
    var cage2: CovariantCage<Animal> = cage1

    // var cage3: ContravariantCage<Bird> = ContravariantCage(animal1)
    // var cage4: ContravariantCage<Animal> = cage3

    // var cageString: Cage<String> = Cage(animal = "this cage hosts a String?", size = 0.1)
    var cageDog: Cage<Dog> = Cage(animal = dog2, size = 6.0)
    var cageCat: Cage<Cat> = Cage(animal = cat2, size = 3.0)

    var cageAnimal: Cage<Animal> = Cage(animal = animal, size = 5.3)
    cageAnimal = cageDog

    // --- collections ---
    val dogList: List<Dog> = listOf()
    var animalList: List<Animal> = dogList  // This assignation is not valid in Java (not covariant)

    val dogMutableList: MutableList<Dog> = mutableListOf()
    // var animalMutableList: MutableList<Animal> = dogMutableList  // This assignation fails as in Java (invariant)

    println("Done!")
}