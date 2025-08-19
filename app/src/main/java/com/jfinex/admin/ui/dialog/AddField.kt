package com.jfinex.admin.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.dialog.components.StyledCard
import com.jfinex.admin.ui.field.Field
import com.jfinex.admin.ui.field.FieldViewModel

@Composable
fun AddFieldDialog(
    onDismiss: () -> Unit,
    viewModel: FieldViewModel = hiltViewModel()
) {
    var fieldNameText by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(emptyList<String>()) }
    var emptyFieldNameWarning by remember { mutableStateOf(false) }
    var notEnoughCategory by remember { mutableStateOf(false) }

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
                    onValueChange = {
                        fieldNameText = it
                        if (emptyFieldNameWarning == true) emptyFieldNameWarning = false
                    },
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
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(start = 10.dp)
                ) {
                    if (fieldNameText.isEmpty()) {
                        item {
                            Text(
                                text = "Field Name is Required",
                                color = if (emptyFieldNameWarning)Color.Red else Color.Gray
                            )
                        }
                    }
                    if (categories.isEmpty()) {
                        item {
                            Text(
                                text = "Add category (Optional)",
                                color = Color.Gray
                            )
                        }
                    }
                    items(categories) {category ->
                        Row(modifier = Modifier.height(30.dp)) {
                            Text(
                                text = "• $category",
                                modifier = Modifier.weight(0.6f),
                                overflow = TextOverflow.Ellipsis
                            )
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Close",
                                modifier = Modifier
                                    .weight(0.2f)
                                    .size(20.dp)
                                    .clickable(onClick = {
                                        categories -= category
                                    })
                            )
                        }
                        HorizontalDivider(color = Color.Gray)
                    }
                    if (categories.size == 1) {
                        item {
                            Text(
                                text = "You need atleast two categories" +
                                        if (fieldNameText.isNotBlank()) " otherwise just name the Field"
                                        else "",
                                color = if (notEnoughCategory) Color.Red else Color.Gray
                            )
                            if (fieldNameText.isNotBlank()) {
                                Text(
                                    text = "$fieldNameText (${categories[0]})",
                                    color = if (notEnoughCategory) Color.Red else Color.Blue,
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier.clickable(
                                        onClick = {
                                            fieldNameText = "$fieldNameText (${categories[0]})"
                                            categories -= categories[0]
                                        }
                                    )
                                )
                            }
                        }
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
                            if (categoryText.isNotBlank()) {
                                categories += categoryText
                                categoryText = ""
                                if (notEnoughCategory == true) notEnoughCategory = false
                            }
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
                    onClick = {
                        when {
                            fieldNameText.isEmpty() -> {
                                emptyFieldNameWarning = true
                            }

                            categories.isNotEmpty() && categories.size == 1 -> {
                                notEnoughCategory = true
                            }

                            else -> {
                                viewModel.addField(
                                    Field(
                                        name = fieldNameText.trim(),
                                        category = categories.map { it.trim() }
                                    )
                                )
                                onDismiss()
                            }
                        }
                    },
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