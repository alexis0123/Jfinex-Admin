package com.jfinex.admin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfinex.admin.data.local.collection.Collection
import com.jfinex.admin.data.local.collection.CollectionDao

@Database(
    entities = [
        Collection::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ListConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
}