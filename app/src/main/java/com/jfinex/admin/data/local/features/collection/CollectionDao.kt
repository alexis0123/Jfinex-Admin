package com.jfinex.admin.data.local.features.collection

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Insert
    suspend fun addCollection(collection: Collection)

    @Delete
    suspend fun deleteCollection(collection: Collection)

    @Update
    suspend fun updateCollection(collection: Collection)

    @Query("SELECT * FROM collections")
    fun getAllCollections(): Flow<List<Collection>>

}