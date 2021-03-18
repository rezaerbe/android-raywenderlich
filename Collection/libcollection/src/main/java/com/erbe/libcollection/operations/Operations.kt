package com.erbe.libcollection.operations

data class Product(
    val id: Int,
    val name: String,
    val price: Double
)

data class Worker(
    val id: Int,
    val name: String
)

class Receipt(
    val id: Int,
    val seller: Worker,
    val products: List<Product>,
    val isPaid: Boolean = false
)

class Store(
    val receipts: List<Receipt>,
    val workers: List<Worker>
)

fun beer() = Product(id = 2, name = "Beer, light, 0.5l", price = 7.5)
fun coffee() = Product(id = 3, name = "Ground coffee 1kg", price = 5.0)
fun bread() = Product(id = 1, name = "Gluten-free bread, 1kg", price = 5.0)

fun main() {
    val firstWorker = Worker(id = 1, name = "Filip")
    val secondWorker = Worker(id = 2, name = "Chris")

    val store = Store(
        receipts = listOf(
            Receipt(
                id = 1,
                seller = firstWorker,
                products = listOf(bread(), bread(), bread(), coffee(), beer()),
                isPaid = true
            ),

            Receipt(
                id = 2,
                seller = secondWorker,
                products = listOf(coffee(), coffee(), beer(), beer(), beer(), beer(), beer()),
                isPaid = false
            ),

            Receipt(
                id = 3,
                seller = secondWorker,
                products = listOf(beer(), beer(), bread()),
                isPaid = false
            )
        ),

        workers = listOf(firstWorker, secondWorker)
    )

    val receipts = store.receipts

    // transforming the data
    val productsLists = receipts.map { it.products } // List<List<Product>> :[
    val allProducts = receipts.flatMap { it.products } // List<Product>

    println(productsLists)
    println(allProducts)

    allProducts.map { it.price } // prices for all the products

    // filtering items
    val paidReceipts = receipts.filter { it.isPaid }
    println(paidReceipts)

    // grouping values
    val paidUnpaid = receipts.partition { it.isPaid }
    val (paid, unpaid) = paidUnpaid
    println(paid)
    println(unpaid)

    val groupByWorker = receipts.groupBy { it.seller } // Map<Worker, List<Receipt>>
    println(groupByWorker)

    // validation
    val areThereNoReceipts = receipts.isEmpty() // also isNotEmpty()
    println(areThereNoReceipts)

    val areAllPaid = receipts.all { it.isPaid }
    println(areAllPaid)
    
    val nonePaid = receipts.none { it.isPaid }
    println(nonePaid)

    val isAtLeastOnePaid = receipts.any { it.isPaid }
    println(isAtLeastOnePaid)

    // looking up values
    val receiptByIndex = receipts[0] // receipts.get(0)
    println(receiptByIndex)

    val firstPaidReceipt = receipts.first { it.isPaid } // will crash if there is none
    println(firstPaidReceipt)

    val firstPaidReceiptOrNull = receipts.firstOrNull { it.isPaid } // either is paid, or null
    println(firstPaidReceiptOrNull)

    val lastByPredicate = receipts.last { !it.isPaid }
    println(lastByPredicate)

    // get total earned money from receipts
    val totalMoney = receipts.filter { it.isPaid }
        .flatMap { it.products }
        .map { it.price } // also sumByDouble { it.price }
        .sum()
    println(totalMoney)

    // all receiptIds by workers
    val receiptIdsByWorkers = receipts
        .groupBy { receipt -> receipt.seller }
        .mapValues { entry -> entry.value.map { receipt -> receipt.id } }
    println(receiptIdsByWorkers)

    // number of products sold per id
    val totalMoneyEarnedPerProduct = receipts
        .flatMap { it.products }
        .groupBy { it.id }
        .mapValues { entry -> entry.value.sumByDouble { product -> product.price } }
    println(totalMoneyEarnedPerProduct)
}