package com.fionasiregar0032.billflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val totalAmount: Float,
    val ppn: Float,
    val service: Float,
    val discount: Float,
    val perPersonJson: String,
    val timestamp: Long = System.currentTimeMillis()
)
