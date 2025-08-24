package com.jfinex.admin.ui.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jfinex.admin.ui.dialog.addStudent.AddStudent
import com.jfinex.admin.ui.dialog.createConfig.CreateConfigDialog
import com.jfinex.admin.ui.dialog.importConfig.ImportConfig

@Composable
fun OptionsMenu() {
    var expanded by remember { mutableStateOf(false) }
    var showCreateConfig by remember { mutableStateOf(false) }
    var showAddStudent by remember { mutableStateOf(false) }
    var showImportConfig by remember { mutableStateOf(false) }

    if (showCreateConfig) {
        CreateConfigDialog(
            onDismiss = { showCreateConfig = false }
        )
    }

    if (showAddStudent) {
        AddStudent(
            onDismiss = { showAddStudent = false }
        )
    }

    if (showImportConfig) {
        ImportConfig(
            onDismiss = { showImportConfig = false }
        )
    }

    Box {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Options",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Column {
                DropdownMenuItem(
                    text = { Text("Create Config") },
                    onClick = {
                        expanded = false
                        showCreateConfig = true
                    }
                )
                DropdownMenuItem(
                text = { Text("Import Config") },
                onClick = {
                        expanded = false
                        showImportConfig = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Add Student") },
                    onClick = {
                        expanded = false
                        showAddStudent = true
                    }
                )

            }
        }
    }
}