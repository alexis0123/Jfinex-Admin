package com.jfinex.admin.data.local.receipt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReceiptDao {

    @Insert
    suspend fun addReceipt(receipt: Receipt)

    @Delete
    suspend fun removeReceipt(receipt: Receipt)

    @Query("SELECT * FROM receipt")
    suspend fun getAllReceipt(): List<Receipt>

}