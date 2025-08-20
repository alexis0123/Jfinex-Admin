package com.jfinex.admin.ui.field

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class Field(
    val name: String,
    val category: List<String> = emptyList()
)

class FieldsRepository @Inject constructor() {
    val fields = mutableListOf<Field>()

    fun addField(field: Field) = fields.add(field)
    fun removeField(field: Field) = fields.remove(field)

}