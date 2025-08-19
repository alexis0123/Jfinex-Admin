package com.jfinex.admin.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.dialog.components.StyledCard
import com.jfinex.admin.ui.field.FieldViewModel

@Composable
fun AddFieldDialog(
    onDismiss: () -> Unit,
    viewModel: FieldViewModel = hiltViewModel()
) {
    var fieldNameText by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(emptyList<String>()) }

    Dialog(onDismissRequest = {}) {
        StyledCard {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .height(360.dp)
                    .width(285.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    value = fieldNameText,
                    onValueChange = { fieldNameText = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Field Name") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        unfocusedIndicatorColor = Color.DarkGray,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        unfocusedLabelColor = Color.DarkGray,
                        cursorColor = MaterialTheme.colorScheme.secondary
                    )
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 50.dp, max = 180.dp)
                        .padding(horizontal = 10.dp)
                ) {
                    if (categories.isEmpty()) {
                        item {
                            Text(
                                text = "No category",
                                color = Color.Gray
                            )
                        }
                    }

                    items(categories) {category ->
                        Text(category)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        value = categoryText,
                        onValueChange = { categoryText = it },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Category Name") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor = Color.DarkGray,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            unfocusedLabelColor = Color.DarkGray,
                            cursorColor = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.width(170.dp)
                    )

                    Button(
                        onClick = {
                            categories += categoryText
                            categoryText = ""
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .width(85.dp)
                            .height(55.dp)
                            .offset(y = 4.dp)
                    ) {
                        Text(
                            text = "Add",
                            color = Color.Black
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Save Field",
                        color = Color.Black
                    )
                }
                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(110.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.Black
                    )
                }
            }

        }
    }
}