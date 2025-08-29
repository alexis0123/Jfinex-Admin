package com.jfinex.admin.ui.pager.page.collection.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.data.local.components.dateToday
import com.jfinex.admin.data.local.components.formattedDate
import com.jfinex.admin.data.local.features.collection.CollectionViewModel
import com.jfinex.admin.data.local.features.students.Student
import com.jfinex.admin.ui.dialog.components.StyledCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun GenerateReceipt(
    onDismiss: () -> Unit,
    viewModel: ReceiptGeneratorViewModel = hiltViewModel()
) {

    val newCollections by viewModel.newCollections.collectAsState()
    val existing by viewModel.alreadyExists.collectAsState()

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = if (newCollections.isNotEmpty()) { "Receipts Generated Successfully" }
            else { "Failed to Generate New Receipt" },
            cardHeight = when {
                newCollections.size + existing.size == 1 ->
                    220.dp
                newCollections.size + existing.size == 2 ->
                    360.dp
                else ->
                    520.dp
            }
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(newCollections) { collection ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(collection.block)
                                    Text(
                                        text = "Receipt",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Check",
                                        tint = Color.Gray,
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${collection.receiptNumber}")
                                    Text(formattedDate(collection.date))
                                }
                                Text("   ${collection.name}")
                                Text(
                                    text = "   ${collection.item}${
                                        if (collection.category != "Paid" && collection.category!!
                                                .isNotBlank()) " (${collection.category})" else ""}"
                                )
                            }
                        }
                    }
                    items(existing) { collection ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(10.dp)
                                )
                                .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(collection.block)
                                    Text(
                                        text = "Receipt Already Exist",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ErrorOutline,
                                        contentDescription = "Error",
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }
                                Text("   ${collection.name}")
                                Text(
                                    text = "   ${collection.item}${
                                        if (collection.category != "Paid" && collection.category!!
                                                .isNotBlank()) " (${collection.category})" else ""}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}