package com.jfinex.admin.data.local.fields

import javax.inject.Inject

class FieldRepository @Inject constructor(
    private val dao: FieldDao
) {
    suspend fun getAll(): List<Field> = dao.getFields()
    suspend fun insert(field: Field) = dao.insert(field)
    suspend fun clear() = dao.clear()
}