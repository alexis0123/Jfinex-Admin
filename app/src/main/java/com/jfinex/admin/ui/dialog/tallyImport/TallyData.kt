package com.jfinex.admin.ui.dialog.tallyImport

import com.jfinex.admin.data.local.features.collection.Collection
import com.jfinex.admin.data.local.features.receipt.Receipt
import com.jfinex.admin.ui.field.Field
import kotlinx.serialization.Serializable

@Serializable
data class TallyData(
    val officerName: String,
    val fields: List<Field>,
    val collections: List<Collection>,
    val receipts: List<Receipt>
)