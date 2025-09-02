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

    private val _block = MutableStateFlow("")
    private val _dates = MutableStateFlow<List<LocalDate>>(emptyList())
    private val _officers = MutableStateFlow<List<String>>(emptyList())
    private val _types = MutableStateFlow<List<String>>(emptyList())
    private val _items = MutableStateFlow<List<String>>(emptyList())

    private val _blockFilter = ""
    private val _dateFilter = emptyList<LocalDate>()
    private val _officerFilter = emptyList<String>()
    private val _typeFilter = emptyList<String>()
    private val _itemFilter = emptyList<String>()

    private val allActivities = repo.getByFilter(
        block = _blockFilter,
        date = _dateFilter,
        officerName = _officerFilter,
        types = _typeFilter,
        item = _itemFilter
    )

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

}