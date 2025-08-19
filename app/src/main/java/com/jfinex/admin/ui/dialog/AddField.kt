package com.jfinex.admin.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.field.FieldViewModel

@Composable
fun AddFieldDialog(
    onDismiss: () -> Unit,
    viewModel: FieldViewModel = hiltViewModel()
) {
    var fieldNameText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .height(500.dp),
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, Color.Black),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {}


                OutlinedTextField(
                    value = fieldNameText,
                    onValueChange = { fieldNameText = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Field Name") }
                )


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
                            text = "Add",
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
}