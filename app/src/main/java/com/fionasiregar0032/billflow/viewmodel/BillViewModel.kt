package com.fionasiregar0032.billflow.viewmodel

import androidx.lifecycle.ViewModel
import com.fionasiregar0032.billflow.model.Bill
import kotlin.random.Random

class BillViewModel : ViewModel() {

    private val bills = mutableListOf<Bill>(
        Bill(
            id = 1,
            totalAmount = 500000.0,
            numberOfPeople = 5,
            names = "Fiona, John, Sarah, Greg, Mike",
            perPersonAmount = 100000.0
        ),
        Bill(
            id = 2,
            totalAmount = 1000000.0,
            numberOfPeople = 8,
            names = "Anna, Bob, Chloe, Dave, Eva, Felix, George, Helen",
            perPersonAmount = 125000.0
        )
    )

    fun getBill(id: Int): Bill? {
        return bills.find { it.id == id }
    }

    fun saveBill(
        name: String,
        totalAmount: String,
        numberOfPeople: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val total = totalAmount.toDoubleOrNull()
        val people = numberOfPeople.toIntOrNull()

        if (name.isEmpty()) {
            onError("Nama tidak boleh kosong")
            return
        }

        if (total == null || total <= 0) {
            onError("Total tagihan harus angka dan lebih dari 0")
            return
        }

        if (people == null || people <= 0) {
            onError("Jumlah orang harus angka dan lebih dari 0")
            return
        }

        val perPersonAmount = total / people

        val newBill = Bill(
            id = Random.nextInt(1000, 9999),  // Menghasilkan ID acak untuk tagihan
            totalAmount = total,
            numberOfPeople = people,
            names = name,
            perPersonAmount = perPersonAmount
        )

        bills.add(newBill)
        onSuccess()
    }

    fun getAllBills(): List<Bill> {
        return bills
    }
}
