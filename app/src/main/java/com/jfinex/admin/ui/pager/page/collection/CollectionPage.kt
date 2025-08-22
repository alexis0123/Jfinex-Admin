package com.jfinex.admin.ui.pager.page.collection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jfinex.admin.ui.pager.page.collection.components.CollectionTextField

@Composable
fun CollectionPage() {

    var blockTextValue by remember { mutableStateOf("") }
    var nameTextValue by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
                .padding(top = 65.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Text(
                    text = "Collection Entry",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = "Collection Entry",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.offset(1.dp, 1.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier
                    .weight(0.25f)
                    .fillMaxHeight()){
                    Text(
                        text = "   Block",
                        modifier = Modifier.weight(0.35f),
                        fontWeight = FontWeight.Bold
                    )
                    CollectionTextField(
                        value = blockTextValue,
                        onValueChange = { blockTextValue = it.take(2).uppercase() },
                        placeholder = "1B",
                        modifier = Modifier.weight(0.65f)
                    )
                }
                Spacer(modifier = Modifier.weight(0.02f))
                Column(modifier = Modifier
                    .weight(0.73f)
                    .fillMaxHeight()){
                    Text(
                        text = "   Student Name",
                        modifier = Modifier.weight(0.35f),
                        fontWeight = FontWeight.Bold
                    )
                    CollectionTextField(
                        value = nameTextValue,
                        onValueChange = { nameTextValue = it },
                        placeholder = "Dela Cruz, Juan",
                        modifier = Modifier.weight(0.65f)
                    )
                }
            }
        }
    }
}