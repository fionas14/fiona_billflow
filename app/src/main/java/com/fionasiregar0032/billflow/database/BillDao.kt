package com.fionasiregar0032.billflow.database

import androidx.room.*
import com.fionasiregar0032.billflow.model.BillEntity

@Dao
interface BillDao {
    @Insert
    suspend fun insert(bill: BillEntity)

    @Query("SELECT * FROM bills ORDER BY timestamp DESC")
    suspend fun getAll(): List<BillEntity>
}
