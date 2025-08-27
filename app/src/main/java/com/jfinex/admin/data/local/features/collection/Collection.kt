package com.jfinex.admin.data.local.features.collection

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val date: LocalDate,
    val name: String,
    val block: String,
    val officerName: String,
    val item: String? = null,
    val category: String? = null,
    val receiptNumber: Int? = null
)