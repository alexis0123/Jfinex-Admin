package com.jfinex.admin.data.local.students

import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val dao: StudentDao
) {
    suspend fun getAll(): List<Student> = dao.getStudents()
    suspend fun insert(student: Student) = dao.insert(student)
    suspend fun clear() = dao.clear()
}