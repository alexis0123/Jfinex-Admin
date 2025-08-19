package com.jfinex.admin.ui.dialog.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StyledCard(
    modifier: Modifier = Modifier,
    titleBarColor: Color = MaterialTheme.colorScheme.background,
    cardColor: Color = MaterialTheme.colorScheme.background,
    titleBarHeight: Dp = 50.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .width(400.dp)
            .height(500.dp),
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.Black),
        shadowElevation = 2.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {}

            content()
        }
    }
}