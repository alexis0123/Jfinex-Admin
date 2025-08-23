package com.jfinex.admin.ui.pager.page.collection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.students.Student
import com.jfinex.admin.data.local.students.StudentRepository
import com.jfinex.admin.ui.pager.page.collection.components.similarity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentSearchViewModel @Inject constructor(
    private val studentRepo: StudentRepository
) : ViewModel() {

    private val allStudents = MutableStateFlow<List<Student>>(emptyList())

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _blockFilter = MutableStateFlow<String?>(null)
    val blockFilter: StateFlow<String?> = _blockFilter

    private val _studentIsSelected = MutableStateFlow(false)
    val studentIsSelected: StateFlow<Boolean> = _studentIsSelected



    init {
        viewModelScope.launch(Dispatchers.IO) {
            allStudents.value = studentRepo.getAll()
        }
    }

    val results: StateFlow<List<Student>> =
        combine(_query, _blockFilter, allStudents) { query, block, students ->
            Triple(query.lowercase(), block, students)
        }
            .debounce(200)
            .map { (query, block, students) ->
                if (query.isBlank()) emptyList()
                else {
                    students.asSequence()
                        .filter { student ->
                            student.name.contains(query, ignoreCase = true) &&
                                    (block.isNullOrBlank() || student.block.equals(block, ignoreCase = true))
                        }
                        .sortedWith(
                            compareByDescending<Student> { it.name.startsWith(query, ignoreCase = true) }
                                .thenBy { similarity(query, it.name.lowercase()) }
                        )
                        .take(50)
                        .toList()
                }
            }
            .flowOn(Dispatchers.Default)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun updateBlock(block: String?) {
        _blockFilter.value = block
    }

    fun selectStudent() {
        _studentIsSelected.value = true
    }

    fun clear() {
        updateQuery("")
        updateBlock("")
        if (_studentIsSelected.value) _studentIsSelected.value = false
    }

}