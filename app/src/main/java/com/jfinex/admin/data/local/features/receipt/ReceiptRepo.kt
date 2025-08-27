package com.jfinex.admin.data.local.features.receipt

import java.time.LocalDate
import javax.inject.Inject

class ReceiptRepo @Inject constructor(
    private val dao: ReceiptDao
) {
    suspend fun addCollection(
        date: LocalDate,
        name: String,
        block: String,
        officerName: String,
        item: String,
        category: String,
        receiptNumber: Int,
    ) {
        dao.addReceipt(
            Receipt(
                date = date,
                name = name,
                block = block,
                officerName = officerName,
                item = item,
                category = category,
                receiptNumber = receiptNumber
            )
        )
    }
}