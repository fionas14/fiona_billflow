package com.fionasiregar0032.billflow.util
import com.fionasiregar0032.billflow.model.PersonWithItems

fun calculateTotal(person: PersonWithItems): Double {
    return person.items.sumOf { it.price }
}

fun calculateGrandTotal(people: List<PersonWithItems>): Double {
    return people.sumOf { calculateTotal(it) }
}

fun calculateServiceCharge(grandTotal: Double, percentage: Double): Double {
    return grandTotal * (percentage / 100)
}

fun calculateDiscount(grandTotal: Double, percentage: Double): Double {
    return grandTotal * (percentage / 100)
}

fun calculateTax(grandTotal: Double, percentage: Double): Double {
    return grandTotal * (percentage / 100)
}

fun calculateFinalTotal(
    grandTotal: Double,
    serviceCharge: Double,
    discount: Double,
    tax: Double
): Double {
    return grandTotal + serviceCharge - discount + tax
}
