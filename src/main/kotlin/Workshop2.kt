package org.example

// 1. กำหนด data class สำหรับเก็บข้อมูลสินค้า
data class Product(val name: String, val price: Double, val category: String)

// ฟังก์ชันที่คำนวณผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท
fun calculateTotalElectronicsPriceOver500(products: List<Product>): Double {
    return products
        .filter { it.category == "Electronics" }
        .filter { it.price > 500.0 }
        .sumOf { it.price }
}

// ฟังก์ชันนับจำนวนสินค้า Electronics ที่ราคา > 500 บาท
fun countElectronicsOver500(products: List<Product>): Int {
    return products.count { it.category == "Electronics" && it.price > 500.0 }
}

fun main() {
    // 2. สร้างรายการสินค้าตัวอย่าง (List<Product>)
    val products = listOf(
        Product("Laptop", 35000.0, "Electronics"),
        Product("Smartphone", 25000.0, "Electronics"),
        Product("T-shirt", 450.0, "Apparel"),
        Product("Monitor", 7500.0, "Electronics"),
        Product("Keyboard", 499.0, "Electronics"), // ราคาไม่เกิน 500
        Product("Jeans", 1200.0, "Apparel"),
        Product("Headphones", 1800.0, "Electronics") // ตรงตามเงื่อนไข
    )

    println("รายการสินค้าทั้งหมด:")
    products.forEach { println(it) }
    println("--------------------------------------------------")

    // 3. เรียกใช้ฟังก์ชันแบบทั่วไป
    val totalElecPriceOver500 = calculateTotalElectronicsPriceOver500(products)
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500 บาท")

    val countElecOver500 = countElectronicsOver500(products)
    println("จำนวนสินค้า Electronics ที่ราคา > 500 บาท: $countElecOver500 ชิ้น")
    println("--------------------------------------------------")

    // 4. (ขั้นสูง) ใช้ .asSequence() เพื่อเพิ่มประสิทธิภาพ
    val totalElecPriceOver500Sequence = products
        .asSequence()
        .filter { it.category == "Electronics" }
        .filter { it.price > 500.0 }
        .map { it.price }
        .sum()

    println("ผลรวมราคาสินค้า (แบบ Sequence): $totalElecPriceOver500Sequence บาท")
    println("--------------------------------------------------")

    // 5. Group by ราคา
    val groupedByPriceRange = products.groupBy { product ->
        when {
            product.price <= 1000 -> "ไม่เกิน 1,000 บาท"
            product.price in 1000.0..9999.0 -> "1,000 - 9,999 บาท"
            else -> "10,000 บาทขึ้นไป"
        }
    }

    println("กลุ่มสินค้าตามช่วงราคา:")
    for ((range, group) in groupedByPriceRange) {
        println("- $range:")
        group.forEach { println("  • ${it.name} (${it.price} บาท)") }
    }
}
