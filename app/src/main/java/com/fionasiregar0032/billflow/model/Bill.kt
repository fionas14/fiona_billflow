package com.fionasiregar0032.billflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills")
data class Bill(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val totalAmount: Double,
    val numberOfPeople: Int,
    val names: String,
    val perPersonAmount: Double,
    val timestamp: Long = System.currentTimeMillis()
)
