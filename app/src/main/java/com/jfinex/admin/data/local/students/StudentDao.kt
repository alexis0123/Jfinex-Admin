package com.jfinex.admin.data.local.students

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {

    @Query("SELECT * FROM students")
    suspend fun getFields(): List<Student>

    @Insert
    suspend fun insert(student: Student)

}