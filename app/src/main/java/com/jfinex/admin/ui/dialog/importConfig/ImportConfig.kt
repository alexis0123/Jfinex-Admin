package com.jfinex.admin.ui.dialog.importConfig

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.ui.config.exportConfig.ConfigViewModel
import com.jfinex.admin.ui.dialog.components.StyledCard
import java.io.File

@Composable
fun ImportConfig(
    onDismiss: () -> Unit,
    viewModel: ConfigViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = onDismiss) {
        StyledCard {
            Column {
                val context = LocalContext.current
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.OpenDocument(),
                    onResult = { uri ->
                        if (uri != null) {
                            val outputFile = File(context.filesDir, "imported_config.json")
                            viewModel.importConfig(context.contentResolver, uri, outputFile)
                            onDismiss()
                        }
                    }
                )
                Button(onClick = { launcher.launch(arrayOf("application/json")) }) {
                    Text("Import Config")
                }
            }
        }
    }
}