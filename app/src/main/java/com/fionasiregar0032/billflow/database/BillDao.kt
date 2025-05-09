package com.fionasiregar0032.billflow.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fionasiregar0032.billflow.model.Bill

@Dao
interface BillDao {

    @Insert
    suspend fun insert(bill: Bill)

    @Query("SELECT * FROM bills ORDER BY timestamp DESC")
    suspend fun getAllBills(): List<Bill>
}
