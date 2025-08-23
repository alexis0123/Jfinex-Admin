package com.jfinex.admin.ui.pager.page.collection

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
                        .toList()
                        .let { filtered ->
                            filtered.sortedBy { similarity(query, it.name.lowercase()) }
                                .take(50)
                        }
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
}