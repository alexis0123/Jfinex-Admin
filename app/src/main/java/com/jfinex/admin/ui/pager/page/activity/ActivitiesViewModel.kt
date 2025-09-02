package com.jfinex.admin.ui.pager.page.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.features.collection.Collection
import com.jfinex.admin.data.local.features.collection.CollectionRepo
import com.jfinex.admin.ui.pager.page.collection.components.similarity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val repo: CollectionRepo
) : ViewModel() {
    private val _blockFilter = MutableStateFlow("")
    val blockFilter: StateFlow<String> = _blockFilter

    private val _dateFilter = MutableStateFlow(emptyList<LocalDate>())
    val dateFilter: StateFlow<List<LocalDate>> = _dateFilter

    private val _officerFilter = MutableStateFlow(emptyList<String>())
    val officerFilter: StateFlow<List<String>> = _officerFilter

    private val _typeFilter = MutableStateFlow(emptyList<String>())
    val typeFilter: StateFlow<List<String>> = _typeFilter

    private val _itemFilter = MutableStateFlow(emptyList<String>())
    val itemFilter: StateFlow<List<String>> = _itemFilter

    private val allActivities: Flow<List<Collection>> =
        combine(
            _blockFilter,
            _dateFilter,
            _officerFilter,
            _typeFilter,
            _itemFilter
        ) { block, date, officer, types, item ->
            repo.getByFilter(
                block = block,
                date = date,
                officerName = officer,
                types = types,
                item = item
            )
        }.flatMapLatest { it }


    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val results: StateFlow<List<Collection>> =
        combine(_query, allActivities) { query, activities ->
            query.lowercase() to activities
        }
            .onEach { _isLoading.value = true }
            .debounce(100)
            .map { (query, activities) ->
                if (query.isBlank()) {
                    activities
                        .sortedByDescending { it.id }
                        .take(50)
                } else {
                    activities.asSequence()
                        .filter { activity ->
                            activity.name.contains(query, ignoreCase = true)
                        }
                        .sortedWith(
                            compareByDescending<Collection> { it.name.startsWith(query, ignoreCase = true) }
                                .thenBy { similarity(query, it.name.lowercase()) }
                        )
                        .take(50)
                        .toList()
                }
            }
            .onEach { _isLoading.value = false }
            .flowOn(Dispatchers.Default)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun clear() {
        _query.value = ""
    }

    fun updateBlockFilter(value: String) {
        _blockFilter.value = value
    }

}