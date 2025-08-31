package com.jfinex.admin.ui.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.unit.dp
import com.jfinex.admin.ui.dialog.about.About
import com.jfinex.admin.ui.dialog.addStudent.AddStudent
import com.jfinex.admin.ui.dialog.collectionData.CollectionData
import com.jfinex.admin.ui.dialog.createConfig.CreateConfigDialog
import com.jfinex.admin.ui.dialog.exportToCsv.ExportData
import com.jfinex.admin.ui.dialog.importConfig.ImportConfig

@Composable
fun OptionsMenu() {
    var expanded by remember { mutableStateOf(false) }
    var showCreateConfig by remember { mutableStateOf(false) }
    var showAddStudent by remember { mutableStateOf(false) }
    var showImportConfig by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }
    var showCollectionData by remember { mutableStateOf(false) }
    var showExportData by remember { mutableStateOf(false) }

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

    if (showAbout) {
        About(
            onDismiss = { showAbout = false }
        )
    }

    if (showCollectionData) {
        CollectionData(
            onDismiss = { showCollectionData = false }
        )
    }

    if (showExportData) {
        ExportData(
            onDismiss = { showExportData = false }
        )
    }

    Box {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Options",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.clickable { expanded = true }.size(30.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
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
                    text = { Text("Collection Data") },
                    onClick = {
                        expanded = false
                        showCollectionData = true
                    }
                )
                DropdownMenuItem(
                        text = { Text("Export Data") },
                    onClick = {
                        expanded = false
                        showExportData = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Add Student") },
                    onClick = {
                        expanded = false
                        showAddStudent = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("About") },
                    onClick = {
                        expanded = false
                        showAbout = true
                    }
                )
            }
        }
    }
}