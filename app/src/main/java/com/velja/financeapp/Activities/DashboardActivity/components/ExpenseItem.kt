package com.velja.financeapp.Activities.DashboardActivity.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExpenseItem(
    item: ExpenseDomain,
    onDelete: (ExpenseDomain) -> Unit = {},
    onEdit: (ExpenseDomain) -> Unit = {}
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    var show by remember { mutableStateOf(true) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                show = false
                onDelete(item)
                true
            } else {
                false
            }
        }
    )
    AnimatedVisibility(
        visible = show,
        exit = shrinkVertically() + fadeOut()
    ) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Red),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            },
            enableDismissFromStartToEnd = false
        ) {
            val resId = try {
                val id = context.resources.getIdentifier(
                    item.pic, "drawable", context.packageName
                )
                if (id != 0) id else R.drawable.btn_1
            } catch (e: Exception) {
                R.drawable.btn_1
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .combinedClickable(
                        onClick = { },
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onEdit(item)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(resId),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(55.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(R.color.lightBlue))
                        .padding(12.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        item.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.darkBlue)
                    )
                    Text(item.time, color = colorResource(R.color.darkBlue))
                }

                Text(
                    "$${item.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.darkBlue),
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpenseItemPreview() {
    val expense = ExpenseDomain(title = "Spotify", price = 12.0, pic = "img1", time = "10:00")
    ExpenseItem(item = expense)
}