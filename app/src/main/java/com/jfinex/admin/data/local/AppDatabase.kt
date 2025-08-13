package com.jfinex.admin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.jfinex.admin.data.local.collection.Collection
import com.jfinex.admin.data.local.collection.CollectionDao

@Database(
    entities = [
        Collection::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
}