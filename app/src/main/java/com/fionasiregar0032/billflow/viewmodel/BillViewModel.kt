package com.fionasiregar0032.billflow.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.fionasiregar0032.billflow.model.PersonWithItems
import com.fionasiregar0032.billflow.util.*

class BillViewModel : ViewModel() {

    private val _people = mutableStateListOf<PersonWithItems>()
    val people: List<PersonWithItems> get() = _people

    var serviceChargePercentage by mutableStateOf("10.0")
    var discountPercentage by mutableStateOf("5.0")
    var taxPercentage by mutableStateOf("11.0")

    val grandTotal: Double
        get() = calculateGrandTotal(people)

    val serviceCharge: Double
        get() = calculateServiceCharge(grandTotal, serviceChargePercentage.toDoubleOrNull() ?: 0.0)

    val discount: Double
        get() = calculateDiscount(grandTotal, discountPercentage.toDoubleOrNull() ?: 0.0)

    val tax: Double
        get() = calculateTax(grandTotal, taxPercentage.toDoubleOrNull() ?: 0.0)

    val finalTotal: Double
        get() = calculateFinalTotal(grandTotal, serviceCharge, discount, tax)

    fun addPerson(person: PersonWithItems) {
        _people.add(person)
    }

    fun clearPeople() {
        _people.clear()
    }

    fun reset() {
        _people.clear()
        serviceChargePercentage = ""
        discountPercentage = ""
        taxPercentage = ""
    }
}

