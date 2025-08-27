package com.jfinex.admin.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfinex.admin.data.local.converters.ListConverter
import com.jfinex.admin.data.local.converters.LocalDateConverter
import com.jfinex.admin.data.local.converters.StudentMapConverter
import com.jfinex.admin.data.local.features.collection.Collection
import com.jfinex.admin.data.local.features.collection.CollectionDao
import com.jfinex.admin.data.local.features.fields.Field
import com.jfinex.admin.data.local.features.fields.FieldDao
import com.jfinex.admin.data.local.features.receipt.Receipt
import com.jfinex.admin.data.local.features.receipt.ReceiptDao
import com.jfinex.admin.data.local.features.students.Student
import com.jfinex.admin.data.local.features.students.StudentDao
import com.jfinex.admin.data.local.features.user.User
import com.jfinex.admin.data.local.features.user.UserDao

@Database(
    entities = [
        Collection::class,
        Student::class,
        Field::class,
        User::class,
        Receipt::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ListConverter::class,
    StudentMapConverter::class,
    LocalDateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
    abstract fun fieldDao(): FieldDao
    abstract fun studentDao(): StudentDao
    abstract fun userDao(): UserDao
    abstract fun receiptDao(): ReceiptDao
}