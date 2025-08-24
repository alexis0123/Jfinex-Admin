package com.jfinex.admin.ui.dialog.addStudent

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.dialog.components.StyledCard

@Composable
fun AddStudent(
    onDismiss: () -> Unit,
    viewModel: StudentViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = onDismiss) {
        StyledCard {
            Column {
                Text("Yo")
            }
        }
    }
}