package com.jfinex.admin.ui.dialog.createConfig

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.csv.CsvViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.jfinex.admin.ui.dialog.components.StyledCard

@Composable
fun ShowFile(
    onDismiss: () -> Unit,
    viewModel: CsvViewModel = hiltViewModel()
) {

    val rows by viewModel.validRows.collectAsState()

    Dialog(onDismissRequest = {}) {

        StyledCard {
            Box(
                modifier = Modifier
                    .height(360.dp)
                    .width(285.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Number of Rows: ${rows.size}",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Block",
                            modifier = Modifier.weight(0.3f),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Name",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    HorizontalDivider(color = Color.Black)
                    LazyColumn {
                        items(rows) { row ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                            ) {
                                row.forEachIndexed { index, item ->
                                    val weight = if (index == 0) 0.3f else 1f
                                    Text(
                                        text = item,
                                        modifier = Modifier.weight(weight),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(285.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = "Ok",
                    color = Color.Black
                )
            }
        }
    }
}