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
        comment: String
    ) {
        dao.addCollection(Collection(
            type = type,
            date = date,
            name = name,
            block = block,
            officerName = officerName,
            item = item,
            category = category,
            receiptNumber = receiptNumber,
            comment = comment
        ))
    }

    suspend fun delete(collection: Collection) {
        dao.deleteCollection(collection)
    }

    suspend fun clear() = dao.clear()

    suspend fun update(collection: Collection) {
        dao.updateCollection(collection = collection)
    }

}