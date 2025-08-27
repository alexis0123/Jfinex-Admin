package com.jfinex.admin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfinex.admin.data.local.collection.Collection
import com.jfinex.admin.data.local.collection.CollectionDao
import com.jfinex.admin.data.local.fields.Field
import com.jfinex.admin.data.local.fields.FieldDao
import com.jfinex.admin.data.local.receipt.Receipt
import com.jfinex.admin.data.local.receipt.ReceiptDao
import com.jfinex.admin.data.local.students.Student
import com.jfinex.admin.data.local.students.StudentDao
import com.jfinex.admin.data.local.user.User
import com.jfinex.admin.data.local.user.UserDao

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