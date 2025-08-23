package com.jfinex.admin.data.local.students

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val dao: StudentDao
) {
    fun getAll(): Flow<List<Student>> = dao.getStudents()
    suspend fun insert(student: Student) = dao.insert(student)
    suspend fun clear() = dao.clear()
}