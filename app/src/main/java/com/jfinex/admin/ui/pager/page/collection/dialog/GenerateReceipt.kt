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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.data.local.components.dateToday
import com.jfinex.admin.data.local.components.formattedDate
import com.jfinex.admin.data.local.features.collection.CollectionViewModel
import com.jfinex.admin.data.local.features.students.Student
import com.jfinex.admin.ui.dialog.components.StyledCard
import java.time.LocalDate

@Composable
fun GenerateReceipt(
    onDismiss: () -> Unit,
    officerName: String,
    student: Student,
    fields: Map<String, String>
) {
    val date = dateToday()

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(title = "Receipts") {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(fields.entries.toList()) { field ->
                        val item = field.key
                        val category = field.value
                        val receiptNumber = student.receiptNumber[item]!!

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
                                Text(student.block)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("$receiptNumber")
                                    Text(formattedDate(date))
                                }
                                Text("   ${student.name}")
                                Text(
                                    text = "   $item${if (category != "Paid" && category.isNotBlank()) " ($category)" else ""}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}