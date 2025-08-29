package com.jfinex.admin.data.local.features.receipt

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "receipt")
data class Receipt(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val block: String,
    val date: LocalDate,
    val item: String,
    val category: String,
    val receiptNumber: Int,
    val officerName: String,
    val new: Boolean = true
)
