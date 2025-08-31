package com.jfinex.admin.ui.dialog.exportToCsv

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.components.StyledButton
import com.jfinex.admin.ui.components.StyledOutlinedButton
import com.jfinex.admin.ui.dialog.components.StyledCard
import com.jfinex.admin.ui.pager.page.collection.FieldViewModel

@Composable
fun ExportData(
    onDismiss: () -> Unit,
    fieldViewModel: FieldViewModel = hiltViewModel(),
    exportDataViewModel: ExportDataViewModel = hiltViewModel(),
    studentBlockViewModel: StudentBlockViewModel = hiltViewModel()
) {

    val fields by fieldViewModel.fields.collectAsState()
    var selectedField by remember { mutableStateOf(fields[0].name) }
    var selectedYear by remember { mutableStateOf<Int?>(null) }
    var selectedBlock by remember { mutableStateOf<String?>(null) }
    var yearAndBlock by remember { mutableStateOf<String?>(null) }
    var fieldExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }
    var blockExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/csv")
    ) { uri ->
        uri?.let {
            exportDataViewModel.export(
                contentResolver = context.contentResolver,
                uri = it,
                fieldName = selectedField,
                yearFilter = selectedYear,
                blockFilter = yearAndBlock
            )
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Export to CSV",
            cardHeight = 270.dp
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                        .clickable(onClick = { fieldExpanded = true })
                ) {
                    Text(selectedField)
                    Icon(
                        imageVector = if (fieldExpanded) {
                            Icons.Default.ExpandLess
                        } else { Icons.Default.ExpandMore },
                        contentDescription = "Expand",
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                    DropdownMenu(
                        expanded = fieldExpanded,
                        onDismissRequest = { fieldExpanded = false },
                        modifier = Modifier
                            .width(280.dp)
                            .heightIn(max = 230.dp)
                    ) {
                        Column {
                            fields.forEach { field ->
                                DropdownMenuItem(
                                    text = { Text(field.name) },
                                    onClick = {
                                        fieldExpanded = false
                                        selectedField = field.name
                                    }
                                )
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp)
                            .clickable(onClick = { yearExpanded = true })
                    ) {
                        Text(
                            text =
                                "${when { 
                                    (selectedYear == null) -> {
                                        "All Year"
                                    }
                                    (selectedYear == 1) -> {
                                        "1st Year"
                                    }
                                    (selectedYear == 2) -> {
                                        "2nd Year"
                                    }
                                    (selectedYear == 3) -> {
                                        "3rd Year"
                                    }
                                    (selectedYear == 4) -> {
                                        "4th Year"
                                    }
                                    else -> selectedYear}}",
                            color = if (selectedYear == null) {
                                Color.LightGray
                            } else { Color.Black }
                        )
                        Icon(
                            imageVector = if (yearExpanded) {
                                Icons.Default.ExpandLess
                            } else { Icons.Default.ExpandMore },
                            contentDescription = "Expand",
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                        DropdownMenu(
                            expanded = yearExpanded,
                            onDismissRequest = { yearExpanded = false },
                            modifier = Modifier
                                .width(120.dp)
                                .heightIn(max = 230.dp)
                        ) {
                            Column {
                                DropdownMenuItem(
                                    text = { Text("All Year") },
                                    onClick = {
                                        yearExpanded = false
                                        selectedYear = null
                                        yearAndBlock = null
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("1st Year") },
                                    onClick = {
                                        yearExpanded = false
                                        selectedYear = 1
                                        yearAndBlock = null
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("2nd Year") },
                                    onClick = {
                                        yearExpanded = false
                                        selectedYear = 2
                                        yearAndBlock = null
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("3rd Year") },
                                    onClick = {
                                        yearExpanded = false
                                        selectedYear = 3
                                        yearAndBlock = null
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("4th Year") },
                                    onClick = {
                                        yearExpanded = false
                                        selectedYear = 4
                                        yearAndBlock = null
                                    }
                                )
                            }
                        }
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(140.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp)
                            .clickable(
                                onClick = { blockExpanded = true },
                                enabled = selectedYear != null
                            )
                    ) {
                        Text(
                            text =
                                "${when {
                                    (selectedBlock == null) -> {
                                        "All Block"
                                    }
                                    else -> selectedBlock}}",
                            color = if (selectedBlock == null) {
                                Color.LightGray
                            } else { Color.Black }
                        )

                        Icon(
                            imageVector = if (blockExpanded) {
                                Icons.Default.ExpandLess
                            } else { Icons.Default.ExpandMore },
                            contentDescription = "Expand",
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                        DropdownMenu(
                            expanded = blockExpanded,
                            onDismissRequest = { blockExpanded = false },
                            modifier = Modifier
                                .width(120.dp)
                                .heightIn(max = 230.dp)
                        ) {
                            Column {
                                DropdownMenuItem(
                                    text = { Text("All Block") },
                                    onClick = {
                                        blockExpanded = false
                                        selectedBlock = null
                                        yearAndBlock = null
                                    }
                                )
                                studentBlockViewModel.getBlocksForYear(selectedYear!!).forEach {
                                    block ->
                                    DropdownMenuItem(
                                        text = { Text(block.drop(1)) },
                                        onClick = {
                                            blockExpanded = false
                                            selectedBlock = block.drop(1)
                                            yearAndBlock = "$selectedYear$selectedBlock"
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StyledButton(
                        onClick = {
                            launcher.launch(
                                when {
                                    (yearAndBlock != null) ->
                                        "$selectedField $yearAndBlock.csv"

                                    (selectedYear != null) -> {
                                        when {
                                            (selectedYear == 1) ->
                                                "$selectedField 1ST YEAR.csv"

                                            (selectedYear == 2) ->
                                                "$selectedField 2ND YEAR.csv"

                                            (selectedYear == 3) ->
                                                "$selectedField 3RD YEAR.csv"

                                            else ->
                                                "$selectedField 4TH YEAR.csv"
                                        }
                                    }

                                    else ->
                                        "$selectedField.csv"
                                }
                            )
                        },
                        name = "Export to CSV",
                        enabled = true,
                        modifier = Modifier
                            .height(60.dp)
                            .width(160.dp)
                    )
                    StyledOutlinedButton(
                        onClick = onDismiss,
                        name = "Cancel",
                        modifier = Modifier
                            .height(60.dp)
                            .width(110.dp)
                    )
                }
            }
        }
    }
}