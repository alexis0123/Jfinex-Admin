package com.jfinex.admin.ui.pager.page.collection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.pager.page.collection.components.CollectionTextField
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import com.jfinex.admin.data.local.components.dateToday
import com.jfinex.admin.data.local.features.collection.CollectionViewModel
import com.jfinex.admin.data.local.features.students.Student
import com.jfinex.admin.data.local.features.user.UserViewModel
import com.jfinex.admin.ui.dialog.setUser.SetUserName
import com.jfinex.admin.ui.pager.page.collection.dialog.GenerateReceipt
import com.jfinex.admin.ui.pager.page.collection.dialog.ReceiptViewModel

@Composable
fun CollectionPage(
    studentViewModel: StudentSearchViewModel = hiltViewModel(),
    fieldViewModel: FieldViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {
    val block by studentViewModel.blockFilter.collectAsState()
    val name by studentViewModel.query.collectAsState()
    val results by studentViewModel.results.collectAsState()
    val fields by fieldViewModel.fields.collectAsState()
    val selectedFields by fieldViewModel.selectedFields.collectAsState()
    val studentIsSelected by studentViewModel.studentIsSelected.collectAsState()
    val isLoading by studentViewModel.isLoading.collectAsState()
    val user by userViewModel.user.collectAsState()
    val newUser by userViewModel.isNewUser.collectAsState()
    var selectedStudent by remember { mutableStateOf<Student?>(null) }
    var showResults by remember { mutableStateOf(false) }
    var showEmptyFieldWarning by remember { mutableStateOf(false) }
    var showEmptyStudentWarning by remember { mutableStateOf(false) }
    var showNoConfigWarning by remember { mutableStateOf(false) }
    var showDisplayReceipt by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
                showResults = false
            }
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .height(40.dp)
                .width(120.dp)
                .border(
                    width = 1.dp, color = Color.Black,
                    shape = RoundedCornerShape(32.dp)
                )
                .align(Alignment.TopEnd)
                .padding(horizontal = 10.dp)
        ) { Text(
            text = user?.name ?: "",
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center)
        ) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
                .padding(top = 65.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Text("Collection Entry", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(
                    "Collection Entry", fontWeight = FontWeight.Bold, fontSize = 25.sp,
                    modifier = Modifier.offset(1.dp, 1.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxHeight()
                ) {
                    Text("   Block", fontWeight = FontWeight.Bold)
                    CollectionTextField(
                        value = block ?: "",
                        onValueChange = {
                            studentViewModel.updateBlock(it.take(2).uppercase())
                            if (showEmptyStudentWarning) showEmptyStudentWarning = false
                        },
                        placeholder = "ex,1B",
                        isEnabled = !studentIsSelected,
                        warning = showEmptyStudentWarning,
                        modifier = Modifier.weight(0.65f)
                    )
                }
                Spacer(modifier = Modifier.weight(0.02f))
                Column(
                    modifier = Modifier
                        .weight(0.73f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("   Student Name", fontWeight = FontWeight.Bold)
                        if (name.isNotBlank()) Text(
                            text = "Clear",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                color = Color.Red,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier.clickable(
                                onClick = {
                                    studentViewModel.clear()
                                    showResults = false
                                }
                            )
                        )
                    }
                    CollectionTextField(
                        value = name,
                        onValueChange = {
                            studentViewModel.updateQuery(it)
                            showResults = it.isNotBlank()
                            if (showEmptyStudentWarning) showEmptyStudentWarning = false
                        },
                        placeholder = "e.g., Dela Cruz, Juan",
                        isEnabled = !studentIsSelected,
                        warning = showEmptyStudentWarning,
                        modifier = Modifier.weight(0.65f)
                    )
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
            ) {
                selectedFields.keys.forEach {
                    if (it !in fields.map { it.name }) {
                        fieldViewModel.removeToSelectedFields(it)
                    }
                }
                if (fields.isEmpty()) {
                    item {
                        Text(
                            text = "  Config Required",
                            color = if (showNoConfigWarning) Color.Red
                            else Color.Gray
                        )
                    }
                }
                items(fields) { field ->
                    var showDropDown by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .background(
                                color = if (field.name in selectedFields) Color.White
                                else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (showEmptyFieldWarning) Color.Red else Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable(onClick = {
                                when {
                                    field.categories.isNotEmpty() && field.name !in selectedFields ->
                                        showDropDown = true

                                    field.name in selectedFields -> {
                                        fieldViewModel
                                            .removeToSelectedFields(field.name)
                                    }

                                    else -> fieldViewModel.addToSelectedFields(field.name, "Paid")
                                }
                                if (showEmptyFieldWarning) showEmptyFieldWarning = false
                            })
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = if (field.name in selectedFields)
                                    Icons.Filled.CheckBox else Icons.Filled.CheckBoxOutlineBlank,
                                contentDescription = "Close",
                                modifier = Modifier
                                    .weight(0.2f)
                                    .size(50.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(0.7f),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = when {
                                        field.name in selectedFields && field.categories.isNotEmpty() ->
                                            "${field.name} (${selectedFields[field.name]})"

                                        else -> field.name
                                    },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                if (field.categories.isNotEmpty() && field.name !in selectedFields)
                                    Text(
                                        text = field.categories.joinToString(", "),
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    )
                            }
                        }
                        DropdownMenu(
                            expanded = showDropDown,
                            onDismissRequest = { showDropDown = false },
                            modifier = Modifier
                                .width(330.dp)
                                .heightIn(max = 185.dp)
                        ) {
                            field.categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category) },
                                    onClick = {
                                        fieldViewModel.addToSelectedFields(field.name, category)
                                        showDropDown = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            HorizontalDivider(color = Color.Black)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        when {
                            fields.isEmpty() ->
                                showNoConfigWarning = true

                            !studentIsSelected ->
                                showEmptyStudentWarning = true

                            selectedFields.isEmpty() ->
                                showEmptyFieldWarning = true

                            else -> {
                                val date = dateToday()
                                selectedFields.forEach { (item, category) ->
                                    val receiptNumber = selectedStudent!!.receiptNumber[item]!!

                                    receiptViewModel.generateReceipt(
                                        date = date,
                                        name = selectedStudent!!.name,
                                        block = selectedStudent!!.block,
                                        officerName = user!!.name,
                                        item = item,
                                        category = category,
                                        receiptNumber = receiptNumber
                                    )
                                    collectionViewModel.addCollection(
                                        type = "Receipt",
                                        date = date,
                                        name = selectedStudent!!.name,
                                        block = selectedStudent!!.block,
                                        officerName = user!!.name,
                                        item = item,
                                        category = category,
                                        receiptNumber = receiptNumber
                                    )
                                }
                                showDisplayReceipt = true
                            }
                        }
                        focusManager.clearFocus()
                    },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .width(185.dp)
                        .height(60.dp)
                ) {
                    Text("Save & Continue", color = Color.Black)
                }
                OutlinedButton(
                    onClick = {
                        studentViewModel.clear()
                        fieldViewModel.clear()
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(125.dp)
                        .height(60.dp)
                ) {
                    Text(
                        text = "Clear",
                        color = Color.Black
                    )
                }
            }
        }



        if (showResults) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .zIndex(1f)
                    .offset(y = 197.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Surface(
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(strokeWidth = 5.dp)
                        }
                    } else if (results.isNotEmpty()) {
                        LazyColumn {
                            items(results) { student ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable {
                                            studentViewModel.updateQuery(student.name)
                                            studentViewModel.updateBlock(student.block)
                                            focusManager.clearFocus()
                                            studentViewModel.selectStudent()
                                            selectedStudent = student
                                            showResults = false
                                        }
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(60.dp),
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    ) {
                                        Text(
                                            text = student.block,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = student.name,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No result for \"$name\".",
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

    if (newUser) {
        SetUserName()
    }

    if (showDisplayReceipt) {
        GenerateReceipt(
            onDismiss = {
                showDisplayReceipt = false
                studentViewModel.clear()
                fieldViewModel.clear()
            },
            officerName = user!!.name,
            student = selectedStudent!!,
            fields = selectedFields
        )
    }

}