package com.jfinex.admin.ui.csv

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CsvImporterScreen(
    viewModel: CsvViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val rows by viewModel.rows.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            viewModel.loadCsv(context.contentResolver, it)
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            launcher.launch(arrayOf("text/csv", "application/csv", "text/comma-separated-values"))
        }) {
            Text("Pick CSV File")
        }

        Spacer(Modifier.height(12.dp))

        if (loading) {
            CircularProgressIndicator()
        }

        error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }

        LazyColumn {
            items(rows) { row ->
                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    row.forEach { item ->
                        Text(
                            text = item,
                            modifier = Modifier.weight(1f),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}