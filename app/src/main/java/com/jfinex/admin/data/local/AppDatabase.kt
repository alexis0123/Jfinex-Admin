package com.jfinex.admin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfinex.admin.data.local.collection.Collection
import com.jfinex.admin.data.local.collection.CollectionDao
import com.jfinex.admin.data.local.fields.Field
import com.jfinex.admin.data.local.fields.FieldDao
import com.jfinex.admin.data.local.students.Student

@Database(
    entities = [
        Collection::class,
        Student::class,
        Field::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ListConverter::class,
    StudentMapConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
    abstract fun fieldDao(): FieldDao
}