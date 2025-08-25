package com.jfinex.admin.ui.dialog.addStudent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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

@Composable
fun AddStudent(
    onDismiss: () -> Unit,
    viewModel: StudentViewModel = hiltViewModel()
) {
    var block by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var middleInitial by remember { mutableStateOf("") }

    var blockWarning by remember { mutableStateOf(false) }
    var lastNameWarning by remember { mutableStateOf(false) }
    var firstNameWarning by remember { mutableStateOf(false) }
    var middleInitialWarning by remember { mutableStateOf(false) }

    val formattedName = if (middleInitial.isBlank()) {
        "${lastName.trim()}, ${firstName.trim()}"
    } else {
        "${lastName.trim()}, ${firstName.trim()} ${middleInitial.trim()}."
    }

    Dialog(onDismissRequest = onDismiss) {
        StyledCard {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = formattedName)

                OutlinedTextField(
                    value = block,
                    onValueChange = {
                        block = it.take(2).uppercase()
                        blockWarning = false
                    },
                    label = { Text("Block") },
                    isError = blockWarning,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        lastNameWarning = false
                    },
                    label = { Text("Last Name") },
                    isError = lastNameWarning,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        firstNameWarning = false
                    },
                    label = { Text("First Name") },
                    isError = firstNameWarning,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = middleInitial,
                    onValueChange = {
                        middleInitial = it.take(1).uppercase()
                        middleInitialWarning = false
                    },
                    label = { Text("Middle Initial") },
                    isError = middleInitialWarning,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            blockWarning = block.isBlank()
                            lastNameWarning = lastName.isBlank()
                            firstNameWarning = firstName.isBlank()

                            val hasError = blockWarning || lastNameWarning || firstNameWarning
                            if (!hasError) {
                                viewModel.addStudent(
                                    block = block.trim(),
                                    name = formattedName
                                )
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .width(160.dp)
                            .height(50.dp)
                    ) {
                        Text("Add Student")
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
}