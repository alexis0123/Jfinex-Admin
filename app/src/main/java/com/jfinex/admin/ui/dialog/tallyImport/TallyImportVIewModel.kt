package com.jfinex.admin.ui.dialog.tallyImport

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TallyImportViewModel @Inject constructor(
    private val repo: TallyImportRepo
) : ViewModel() {

    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val importState: StateFlow<ImportState> = _importState.asStateFlow()

    fun import(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            val result = repo.importTally(uri, contentResolver)
            _importState.value = result.fold(
                onSuccess = { ImportState.Success },
                onFailure = { ImportState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun reset() {
        _importState.value = ImportState.Idle
    }
}

sealed class ImportState {
    object Idle : ImportState()
    object Loading : ImportState()
    object Success : ImportState()
    data class Error(val message: String) : ImportState()
}