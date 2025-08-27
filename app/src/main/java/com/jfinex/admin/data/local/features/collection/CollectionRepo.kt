package com.jfinex.admin.data.local.features.collection

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class CollectionRepo @Inject constructor(
    private val dao: CollectionDao
) {

    fun getAll(): Flow<List<Collection>> = dao.getAllCollections()

    suspend fun addStudent(
        date: LocalDate,
        name: String,
        block: String,
        officerName: String
    ) {
        dao.addCollection(Collection(
            type = "Added Student",
            date = date,
            name = name,
            block = block,
            officerName = officerName
        ))
    }

    suspend fun addCollection(
        type: String,
        date: LocalDate,
        name: String,
        block: String,
        officerName: String,
        item: String,
        category: String,
        receiptNumber: Int,
    ) {
        dao.addCollection(Collection(
            type = type,
            date = date,
            name = name,
            block = block,
            officerName = officerName,
            item = item,
            category = category,
            receiptNumber = receiptNumber
        ))
    }

    suspend fun delete(collection: Collection) {
        dao.deleteCollection(collection)
    }

}