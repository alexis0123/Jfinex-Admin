package com.jfinex.admin.ui.dialog.addStudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.fields.Field
import com.jfinex.admin.data.local.fields.FieldRepository
import com.jfinex.admin.data.local.students.Student
import com.jfinex.admin.data.local.students.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val studentRepo: StudentRepository,
    private val fieldRepo: FieldRepository
): ViewModel() {
    val fields: StateFlow<List<Field>> = fieldRepo.getAll()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addStudent(name: String, block: String) {
        viewModelScope.launch {

            if (fields.value.isEmpty()) return@launch

            val receiptNumbers = fields.value.associate { field ->
                field.name to (field.newBase..field.newBase + 9_999).random()
            }
            studentRepo.insert(
                Student(
                    block = block,
                    name = name,
                    receiptNumber = receiptNumbers
                )
            )
        }
    }
}