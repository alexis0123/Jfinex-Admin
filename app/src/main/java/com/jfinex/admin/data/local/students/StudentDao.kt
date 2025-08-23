package com.jfinex.admin.data.local.students

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {

    @Query("SELECT * FROM students")
    suspend fun getStudents(): List<Student>

    @Insert
    suspend fun insert(student: Student)

    @Query("DELETE FROM students")
    suspend fun clear()

}