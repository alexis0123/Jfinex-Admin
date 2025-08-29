package com.jfinex.admin.ui.dialog.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jfinex.admin.ui.dialog.components.StyledCard

@Composable
fun About(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "About",
            cardHeight = 350.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "About JFINEX COLLECTION",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "JFINEX Collection is a collection system built to support student organizations " +
                            "with efficient record-keeping, receipt tracking, " +
                            "and data management.",
                    style = MaterialTheme.typography.bodyMedium
                )

                val uriHandler = LocalUriHandler.current
                val annotatedText = buildAnnotatedString {
                    append("This app was designed, developed, and maintained by ")

                    pushStringAnnotation("URL", "https://alexis0123.github.io/")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue
                        )
                    ) {
                        append("Alexis Buban")
                    }
                    pop()

                    append(", with a focus on reliability, simplicity, and offline-first functionality " +
                            "to make collection processes smoother and more accessible.")
                }

                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations("URL", offset, offset)
                            .firstOrNull()?.let { uriHandler.openUri(it.item) }
                    }
                )
            }
        }
    }
}