package com.jfinex.admin.ui.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.UploadFile
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
import com.jfinex.admin.ui.dialog.totalsPerCategory.CollectionData
import com.jfinex.admin.ui.dialog.createNewSetup.CreateConfigDialog
import com.jfinex.admin.ui.dialog.downloadData.ExportData
import com.jfinex.admin.ui.dialog.loadSetup.ImportConfig
import com.jfinex.admin.ui.dialog.tallyImport.ImportTally

@Composable
fun OptionsMenu() {
    var expanded by remember { mutableStateOf(false) }
    var showCreateConfig by remember { mutableStateOf(false) }
    var showAddStudent by remember { mutableStateOf(false) }
    var showImportConfig by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }
    var showCollectionData by remember { mutableStateOf(false) }
    var showExportData by remember { mutableStateOf(false) }
    var showImportTally by remember { mutableStateOf(false) }

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

    if (showImportTally) {
        ImportTally(
            onDismiss = { showImportTally = false }
        )
    }

    Box {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Options",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .clickable { expanded = true }
                .size(30.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
        ) {
            Column {
                DropdownMenuItem(
                    text = { Text("Create New Setup") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Create"
                        )
                    },
                    onClick = {
                        expanded = false
                        showCreateConfig = true
                    }
                )
                DropdownMenuItem(
                text = { Text("Load Setup") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FileOpen,
                            contentDescription = "Set"
                        )
                    },
                onClick = {
                        expanded = false
                        showImportConfig = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Import Tally") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Receipt,
                            contentDescription = "Import Receipt"
                        )
                    },
                    onClick = {
                        expanded = false
                        showImportTally = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Totals per Category") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PieChart,
                            contentDescription = "Totals"
                        )
                    },
                    onClick = {
                        expanded = false
                        showCollectionData = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Download Data") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FileDownload,
                            contentDescription = "Download to file"
                        )
                    },
                    onClick = {
                        expanded = false
                        showExportData = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Add Student") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PersonAddAlt1,
                            contentDescription = "Add student"
                        )
                    },
                    onClick = {
                        expanded = false
                        showAddStudent = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("About") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info"
                        )
                    },
                    onClick = {
                        expanded = false
                        showAbout = true
                    }
                )
            }
        }
    }
}