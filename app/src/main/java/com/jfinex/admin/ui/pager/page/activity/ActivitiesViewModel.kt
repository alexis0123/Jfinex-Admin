package com.jfinex.admin.ui.pager.page.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.admin.data.local.features.collection.Collection
import com.jfinex.admin.data.local.features.collection.CollectionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val repo: CollectionRepo
): ViewModel() {
    val activities: StateFlow<List<Collection>> = repo.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val sample = MutableStateFlow<List<Collection>>(listOf(
        Collection(
            id = 1,
            type = "Receipt",
            date = LocalDate.of(2025, 8, 27),
            name = "Buban, Alexis A.",
            block = "2H",
            officerName = "Alexis",
            item = "Sample",
            category = "1",
            receiptNumber = 1000101
        ),
        Collection(
            id = 2,
            type = "Receipt",
            date = LocalDate.of(2025, 8, 27),
            name = "Pantaleon, Lorie Jane A.",
            block = "2H",
            officerName = "Alex",
            item = "Sample1",
            category = "Paid",
            receiptNumber = 1000102
        ),
        Collection(
            id = 3,
            type = "Added Student",
            date = LocalDate.of(2025, 8, 27),
            name = "Dela Cruz, Juan",
            block = "1H",
            officerName = "Alexis"
        ),
        Collection(
            id = 4,
            type = "Voided Receipt",
            date = LocalDate.of(2025, 8, 27),
            name = "Tahimik, Mark",
            block = "3H",
            officerName = "Alexis",
            item = "Sample1",
            category = "Paid",
            receiptNumber = 1000103
        )
    ))
}