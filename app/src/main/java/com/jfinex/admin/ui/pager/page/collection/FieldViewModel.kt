package com.jfinex.admin.ui.pager.page.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.features.fields.Field
import com.jfinex.admin.data.local.features.fields.FieldRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FieldViewModel @Inject constructor(
    private val repo: FieldRepository): ViewModel() {

    val fields: StateFlow<List<Field>> = repo.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedFields = MutableStateFlow<Map<String, String>>(emptyMap())
    val selectedFields: StateFlow<Map<String, String>> = _selectedFields

    fun addToSelectedFields(fieldName: String, category: String) {
        _selectedFields.value += mapOf(fieldName to category)
    }

    fun removeToSelectedFields(fieldName: String) {
        _selectedFields.value -= fieldName
    }

    fun clear() {
        _selectedFields.value = mapOf()
    }

}