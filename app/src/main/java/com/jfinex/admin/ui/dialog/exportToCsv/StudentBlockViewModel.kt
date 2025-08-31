package com.jfinex.admin.ui.dialog.exportToCsv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.features.students.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentBlockViewModel @Inject constructor(
    private val studentRepo: StudentRepository
) : ViewModel() {

    private val _yearBlocks = MutableStateFlow<Map<Int, List<String>>>(emptyMap())
    val yearBlocks: StateFlow<Map<Int, List<String>>> = _yearBlocks.asStateFlow()

    init {
        fetchYearBlocks()
    }

    private fun fetchYearBlocks() {
        viewModelScope.launch {
            studentRepo.getAll()
                .map { students ->
                    students.map { it.block }
                        .distinct()
                        .filter { it.matches(Regex("[1-4][A-Z]")) }
                        .groupBy { it.first().digitToInt() }
                        .mapValues { entry -> entry.value.sorted() }
                }
                .collect { _yearBlocks.value = it }
        }
    }

    fun getBlocksForYear(year: Int): List<String> {
        return _yearBlocks.value[year] ?: emptyList()
    }
}