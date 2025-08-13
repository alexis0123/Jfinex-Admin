package com.jfinex.admin.data.local.collection

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionRepository @Inject constructor(
    private val collectionDao: CollectionDao
) {

    fun getAll(): Flow<List<Collection>> = collectionDao.getAllCollections()

    suspend fun add(collection: Collection) {
        collectionDao.addCollection(collection)
    }

    suspend fun delete(collection: Collection) {
        collectionDao.deleteCollection(collection)
    }

}