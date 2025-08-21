package com.jfinex.admin.ui.dialog

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.csv.CsvViewModel
import com.jfinex.admin.ui.field.FieldViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.jfinex.admin.ui.config.ConfigViewModel
import com.jfinex.admin.ui.dialog.components.StyledCard

@Composable
fun CreateConfigDialog(
    onDismiss: () -> Unit,
    csvViewModel: CsvViewModel = hiltViewModel(),
    fieldViewModel: FieldViewModel = hiltViewModel(),
    configViewModel: ConfigViewModel = hiltViewModel()
) {
    val fields by fieldViewModel.fields.collectAsState()

    val context = LocalContext.current
    val hasFile by csvViewModel.hasFile.collectAsState()
    val fileName by csvViewModel.fileName.collectAsState()
    val loading by csvViewModel.loading.collectAsState()
    var showFile by remember { mutableStateOf(false) }
    var showAddField by remember { mutableStateOf(false) }
    var showHelpCsv by remember { mutableStateOf(false) }
    var emptyCsvWarning by remember { mutableStateOf(false) }
    var emptyFieldWarning by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            csvViewModel.loadCsv(context.contentResolver, it)
        }
    }

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { uri ->
            if (uri != null) {
                val snapshot = csvViewModel.validRows.value.toList()
                configViewModel.exportConfig(context.contentResolver, uri, snapshot)
            } else {
                Toast.makeText(context, "Export cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

// Collect the export result
    val exportResult by configViewModel.exportResult.collectAsState()

    LaunchedEffect(exportResult) {
        exportResult?.let { result ->
            result.onSuccess {
                Toast.makeText(context, "Successfully exported!", Toast.LENGTH_LONG).show()
                onDismiss()
                fieldViewModel.reset()
            }.onFailure { e ->
                Toast.makeText(context, "Failed to export: ${e.message}", Toast.LENGTH_LONG).show()
            }
            // Reset after handling
            configViewModel.reset()
        }
    }

    if (showFile) {
        ShowFile(
            onDismiss = { showFile = false }
        )
    }

    if (showAddField) {
        AddFieldDialog(
            onDismiss = { showAddField = false }
        )
    }

    Dialog(onDismissRequest = {}) {

        StyledCard {
            Box(
                modifier = Modifier
                    .height(360.dp)
                    .width(285.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (!hasFile) {
                                launcher.launch(
                                    arrayOf(
                                        "text/csv",
                                        "application/csv",
                                        "text/comma-separated-values"
                                    )
                                )
                                if (emptyCsvWarning) emptyCsvWarning = false
                            } else {
                                showFile = true
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .width(190.dp)
                            .height(50.dp)
                    ) {
                        when {
                            loading -> {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp))
                            }

                            !hasFile -> {
                                Icon(
                                    imageVector = Icons.Default.AttachFile,
                                    contentDescription = "Attach",
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Text("Pick CSV File")
                            }

                            else -> {
                                Text(
                                    text = "$fileName",
                                    color = Color.Black,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Clear",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(start = 6.dp)
                                        .clickable(onClick = { csvViewModel.clear() })
                                )
                            }
                        }
                    }
                    Button(
                        onClick = { showHelpCsv = true },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier
                            .width(70.dp)
                            .height(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Help,
                            contentDescription = "Help"
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .align(Alignment.Center)
                ) {
                    LazyColumn {
                        if (!hasFile) {
                            item {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)) {
                                    Text(
                                        text = "Students list is Required",
                                        color = if (emptyCsvWarning) Color.Red else Color.Gray
                                    )
                                }
                            }
                        }
                        if (fields.isEmpty()) {
                            item {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)) {
                                    Text(
                                        text = "Add atleast one field",
                                        color = if (emptyFieldWarning) Color.Red else Color.Gray
                                    )
                                }
                            }
                        }
                        items(fields) { field ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = field.name,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        if (field.category.isNotEmpty()) {
                                            Text(
                                                text = "Categories: ${field.category.joinToString(", ")}",
                                                color = Color.DarkGray,
                                                fontSize = 12.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Clear",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable { fieldViewModel.removeField(field) }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }

                Button(
                    onClick = {
                        showAddField = true
                        if (emptyFieldWarning) emptyFieldWarning = false
                    },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = " Add Field",
                        color = Color.Black
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        when {
                            !hasFile -> emptyCsvWarning = true
                            !configViewModel.configRepo.hasFields() -> emptyFieldWarning = true
                            else -> exportLauncher.launch("Config.json")
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(50.dp)
                ) {
                    Text("Create Config", color = Color.Black)
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