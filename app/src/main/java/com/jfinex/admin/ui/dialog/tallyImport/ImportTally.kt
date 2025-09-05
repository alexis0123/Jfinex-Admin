package com.jfinex.admin.ui.dialog.tallyImport

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jfinex.admin.ui.components.StyledButton
import com.jfinex.admin.ui.components.StyledOutlinedButton
import com.jfinex.admin.ui.dialog.components.StyledCard

@Composable
fun ImportTally(
    onDismiss: () -> Unit,
    viewModel: TallyImportViewModel = hiltViewModel<TallyImportViewModel>()
) {
    val context = LocalContext.current
    val importState by viewModel.importState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                viewModel.import(uri, context.contentResolver)
            }
        }
    )

    // 🔄 React to state changes
    LaunchedEffect(importState) {
        when (val state = importState) {
            is ImportState.Success -> {
                val msg = "Tally imported successfully!"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Log.d("TallyImport", msg)

                viewModel.reset()
                onDismiss()
            }
            is ImportState.Error -> {
                val msg = "Import failed: ${state.message}"
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                Log.e("TallyImport", msg)

                viewModel.reset()
            }
            else -> Unit
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Import Tally",
            cardHeight = 160.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    StyledButton(
                        onClick = { launcher.launch(arrayOf("application/json")) },
                        name = if (importState is ImportState.Loading) "Importing..." else "Choose File",
                        enabled = importState !is ImportState.Loading,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                    )
                    StyledOutlinedButton(
                        onClick = onDismiss,
                        name = "Cancel",
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(110.dp)
                    )
                }
            }
        }
    }
}