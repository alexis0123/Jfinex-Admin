package com.jfinex.admin.ui.pager.page.collection.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.features.receipt.Receipt
import com.jfinex.admin.data.local.features.receipt.ReceiptRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repo: ReceiptRepo
): ViewModel() {

    fun generateReceipt(
        date: LocalDate,
        name: String,
        block: String,
        officerName: String,
        item: String,
        category: String,
        receiptNumber: Int
    ) {
        viewModelScope.launch {
            repo.addReceipt(
                date = date,
                name = name,
                block = block,
                officerName = officerName,
                item = item,
                category = category,
                receiptNumber = receiptNumber
            )
        }
    }

    suspend fun removeReceipt(receipt: Receipt) {
        repo.removeReceipt(receipt = receipt)
    }

}