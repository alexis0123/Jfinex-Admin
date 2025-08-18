package com.jfinex.admin.ui.field

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FieldViewModel @Inject constructor(private val repo: FieldsRepository): ViewModel() {
    private val _fields = MutableStateFlow<List<Field>>(
        listOf(
            Field(name = "Membership"),
            Field(
                name = "T-shirt",
                category = listOf("Small", "Medium", "Large")
            ),
            Field(
                name = "T-shirt",
                category = listOf("S", "M", "L")
            ),
            Field(
                name = "T-shirt",
                category = listOf("Small", "Medium", "Large")
            )
        )
    )
    val fields = _fields.asStateFlow()

    fun addField(field: Field) {
        repo.addField(field)
        _fields.value += field
    }

    fun removeField(field: Field) {
        repo.removeField(field)
        _fields.value -= field
    }

}