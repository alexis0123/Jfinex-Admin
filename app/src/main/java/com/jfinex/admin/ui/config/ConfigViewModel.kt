package com.jfinex.admin.ui.config

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    val configRepo: ConfigRepository
) : ViewModel() {

    private val _exportResult = MutableStateFlow<Result<Unit>?>(null)
    val exportResult: StateFlow<Result<Unit>?> = _exportResult

    fun exportConfig(
        contentResolver: ContentResolver,
        uri: Uri,
        students: List<List<String>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Validation
                if (!configRepo.hasFields()) throw IllegalStateException("No fields added")
                if (students.isEmpty()) throw IllegalStateException("No students to export")

                val config = configRepo.buildConfig(students)
                val json = Json { prettyPrint = false }
                    .encodeToString(ConfigExport.serializer(), config)

                Log.d("ConfigExport", "JSON length=${json.length}\n$json")

                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(json.toByteArray())
                } ?: throw IllegalArgumentException("Unable to open OutputStream for URI: $uri")

                _exportResult.value = Result.success(Unit)
            } catch (e: Exception) {
                _exportResult.value = Result.failure(e)
            }
        }
    }
    fun reset() {
        _exportResult.value = null
    }
}