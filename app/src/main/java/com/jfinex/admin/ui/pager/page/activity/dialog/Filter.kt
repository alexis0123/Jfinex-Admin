package com.jfinex.admin.ui.pager.page.activity.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.components.StyledButton
import com.jfinex.admin.ui.dialog.components.StyledCard
import com.jfinex.admin.ui.pager.page.activity.ActivitiesViewModel
import com.jfinex.admin.ui.pager.page.collection.components.CollectionTextField

@Composable
fun Filter(
    onDismiss: () -> Unit,
    activitiesViewModel: ActivitiesViewModel = hiltViewModel()
) {
    var blockFilter by remember { mutableStateOf(activitiesViewModel.blockFilter.value) }
    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Filter",
            cardHeight = 300.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                CollectionTextField(
                    value = blockFilter,
                    onValueChange = { blockFilter = it.uppercase() },
                    placeholder = "Block",
                    isEnabled = true,
                    warning = false
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StyledButton(
                        onClick = {
                            activitiesViewModel.updateBlockFilter(blockFilter)
                        },
                        name = "Apply",
                        enabled = true
                    )
                    StyledButton(
                        onClick = {
                            activitiesViewModel.updateBlockFilter(blockFilter)
                        },
                        name = "Clear",
                        enabled = true
                    )
                }
            }
        }
    }
}