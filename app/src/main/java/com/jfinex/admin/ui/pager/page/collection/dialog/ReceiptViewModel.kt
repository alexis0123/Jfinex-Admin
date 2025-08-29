package com.jfinex.admin.ui.pager.page.collection.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.features.receipt.Receipt
import com.jfinex.admin.data.local.features.receipt.ReceiptRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repo: ReceiptRepo
): ViewModel() {

    private var _receipt = MutableStateFlow<Receipt?>(null)
    val receipt: StateFlow<Receipt?> = _receipt

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

    fun removeReceipt(receipt: Receipt) {
        viewModelScope.launch {
            repo.removeReceipt(receipt = receipt)
        }
    }

    fun getReceipt(name: String, block: String, item: String) {
        viewModelScope.launch {
            _receipt.value = repo.getReceipt(name = name, block = block, item = item)
        }
    }

    fun clear() {
        _receipt.value = null
    }

    fun removeByNameBlockItem(name: String, block: String, item: String) {
        viewModelScope.launch {
            val receipt = repo.getReceipt(name, block, item)
            if (receipt != null) {
                repo.removeReceipt(receipt)
            }
        }
    }


}