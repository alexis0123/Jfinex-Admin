package com.jfinex.admin.ui.dialog.collectionData

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.dialog.components.StyledCard
import com.jfinex.admin.ui.pager.page.collection.FieldViewModel
import com.jfinex.admin.ui.pager.page.collection.dialog.receipt.ReceiptViewModel

@Composable
fun CollectionData(
    onDismiss: () -> Unit,
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    fieldViewModel: FieldViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        receiptViewModel.getAll()
    }
    val receipts by receiptViewModel.receipts.collectAsState()
    val fields by fieldViewModel.fields.collectAsState()

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Collection Data"
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
                    items(fields) { field ->
                        var expanded by remember { mutableStateOf(false) }
                        val categories = field.categories
                        var totalPerCategory: Map<String, Int> = receipts.groupingBy {
                            it.category
                        }.eachCount()
                        var receiptTotal = 0
                        receipts.forEach {
                            if (field.name == it.item) receiptTotal += 1
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    if (expanded) {
                                        (90 + (36 * categories.size)).dp
                                    } else {
                                        50.dp
                                    }
                                )
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                                .clickable(
                                    onClick = {
                                        if (field.categories.isNotEmpty()) expanded = !expanded
                                    },
                                    enabled = field.categories.isNotEmpty()
                                )
                        ) {
                            if (expanded) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(horizontal = 15.dp)
                                    ) {
                                        Text(field.name, modifier = Modifier.weight(5f))
                                        Text(
                                            text = "",
                                            textAlign = TextAlign.End,
                                            modifier = Modifier.weight(5f))
                                        Icon(
                                            imageVector = Icons.Filled.ExpandLess,
                                            contentDescription = "Expand",
                                            modifier = Modifier.weight(2f)
                                        )
                                    }
                                    categories.forEach { category ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 15.dp)
                                                .padding(end = 35.dp)
                                        ) {
                                            Text("    $category")
                                            Text(
                                                text = " - - - - - - - - - - - - - - - - - - - - - - - - - - - ",
                                                color = Color.LightGray,
                                                maxLines = 1,
                                                overflow = TextOverflow.Clip,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text((totalPerCategory[category] ?: 0).toString())
                                        }
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 15.dp)
                                            .padding(end = 35.dp)
                                    ) {
                                        Text(text = "Total", fontWeight = FontWeight.Bold)
                                        Text(
                                            text = " - - - - - - - - - - - - - - - - - - - - - - - - - - - ",
                                            color = Color.LightGray,
                                            maxLines = 1,
                                            overflow = TextOverflow.Clip,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(receiptTotal.toString(), fontWeight = FontWeight.Bold)
                                    }
                                }
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 15.dp)
                                ) {
                                    Text(field.name, modifier = Modifier.weight(5f))
                                    Text(
                                        text = receiptTotal.toString(),
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.weight(5f))
                                    if (categories.isNotEmpty()){
                                        Icon(
                                            imageVector = Icons.Filled.ExpandMore,
                                            contentDescription = "Expand",
                                            modifier = Modifier.weight(2f)
                                        )
                                    } else {
                                        Box(modifier = Modifier.weight(2f)) {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}