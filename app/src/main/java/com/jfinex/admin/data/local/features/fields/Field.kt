package com.jfinex.admin.data.local.features.fields

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class Field(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val categories: List<String>,
    val newBase: Int
)
