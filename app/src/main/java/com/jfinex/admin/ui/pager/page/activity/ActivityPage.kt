package com.jfinex.admin.ui.pager.page.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.admin.data.local.components.formattedDate
import com.jfinex.admin.data.local.features.user.UserViewModel
import com.jfinex.admin.ui.pager.page.collection.components.CollectionTextField

@Composable
fun ActivityPage(
    activityViewModel: ActivitiesViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {

    val userName by userViewModel.user.collectAsState()
    val activities by activityViewModel.results.collectAsState()
    val query by activityViewModel.query.collectAsState()
    val isLoading by activityViewModel.isLoading.collectAsState()

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
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
                .align(Alignment.TopStart)
                .padding(horizontal = 10.dp)
        ) { Text(
            text = "Export",
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
                Text("Activities", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(
                    "Activities", fontWeight = FontWeight.Bold, fontSize = 25.sp,
                    modifier = Modifier.offset(1.dp, 1.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxHeight()
                ) {
                    Text("   Filter", fontWeight = FontWeight.Bold)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.65f)
                            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
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
                        Text("   Search", fontWeight = FontWeight.Bold)
                        if (query.isNotBlank()) Text(
                            text = "Clear",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                color = Color.Red,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier.clickable(
                                onClick = {
                                    activityViewModel.clear()
                                    focusManager.clearFocus()
                                }
                            )
                        )
                    }
                    CollectionTextField(
                        value = query,
                        onValueChange = { activityViewModel.updateQuery(it) },
                        placeholder = "Search by Student Name",
                        isEnabled = true,
                        warning = false,
                        modifier = Modifier.weight(0.65f)
                    )
                }
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(345.dp)) {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 5.dp)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (activities.isEmpty()) {
                            item {
                                Text("No Activities")
                            }
                        }
                        items(activities) { activity ->
                            Surface(
                                color = when {
                                    activity.type == "Receipt" && activity.officerName == userName?.name ->
                                        Color.White

                                    activity.type == "Receipt" ->
                                        Color.White.copy(alpha = 0.5f)

                                    activity.type == "Voided Receipt" ->
                                        Color.Red.copy(alpha = 0.2f)

                                    else ->
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                ) {
                                    if (activity.type == "Receipt" || activity.type == "Voided Receipt") {
                                        Text(activity.block)
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("${activity.receiptNumber}")
                                            Text(formattedDate(activity.date))
                                        }
                                        Text("   ${activity.name}")
                                        Text(
                                            text = "   ${activity.item}${if (activity.category != "Paid" && !activity.category.isNullOrBlank()) " (${activity.category})" else ""}"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HorizontalDivider(color = Color.Black)
        }
    }
}