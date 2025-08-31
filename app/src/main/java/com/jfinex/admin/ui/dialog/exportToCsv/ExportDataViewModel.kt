package com.jfinex.admin.ui.dialog.exportToCsv

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExportDataViewModel @Inject constructor(
    private val repo: ExportDataRepository
) : ViewModel() {

    private val _isExporting = MutableStateFlow(false)
    val isExporting: StateFlow<Boolean> = _isExporting

    fun export(
        contentResolver: ContentResolver,
        uri: Uri,
        fieldName: String,
        yearFilter: Int? = null,
        blockFilter: String? = null
    ) {
        viewModelScope.launch {
            _isExporting.value = true
            try {
                repo.exportData(contentResolver, uri, fieldName, yearFilter, blockFilter)
            } finally {
                _isExporting.value = false
            }
        }
    }
}
