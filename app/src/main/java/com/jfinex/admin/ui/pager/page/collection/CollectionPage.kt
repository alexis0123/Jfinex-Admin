package com.jfinex.admin.ui.pager.page.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.pager.page.collection.components.CollectionTextField
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun CollectionPage(
    viewModel: StudentSearchViewModel = hiltViewModel()
) {
    val block by viewModel.blockFilter.collectAsState()
    val name by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()
    var showResults by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
                showResults = false
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
                .padding(top = 65.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Collection Entry", fontWeight = FontWeight.Bold, fontSize = 25.sp)

            Row(
                modifier = Modifier.fillMaxWidth().height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(0.25f).fillMaxHeight()
                ) {
                    Text("   Block", fontWeight = FontWeight.Bold)
                    CollectionTextField(
                        value = block ?: "",
                        onValueChange = {
                            viewModel.updateBlock(it.take(2).uppercase())
                        },
                        placeholder = "ex,1B",
                        modifier = Modifier.weight(0.65f)
                    )
                }
                Spacer(modifier = Modifier.weight(0.02f))
                Column(
                    modifier = Modifier.weight(0.73f).fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("   Student Name", fontWeight = FontWeight.Bold)
                        if (name.isNotBlank()) Text(
                            text = "Clear",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                color = Color.Red,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier.clickable(
                                onClick = {
                                    viewModel.updateBlock("")
                                    viewModel.updateQuery("")
                                }
                            )
                        )
                    }
                    CollectionTextField(
                        value = name,
                        onValueChange = {
                            viewModel.updateQuery(it)
                            showResults = it.isNotBlank()
                        },
                        placeholder = "e.g., Dela Cruz, Juan",
                        modifier = Modifier.weight(0.65f)
                    )
                }
            }
        }

        // Lazy list overlay
        if (showResults && results.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .offset(y = 150.dp)
                    .wrapContentHeight()
            ) {
                Surface(
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 45.dp)
                        .heightIn(max = 400.dp)
                ) {
                    LazyColumn {
                        items(results) { student ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        viewModel.updateQuery(student.name)
                                        viewModel.updateBlock(student.block)
                                        focusManager.clearFocus()
                                        showResults = false
                                    }
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(60.dp),
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = student.block,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = student.name,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}