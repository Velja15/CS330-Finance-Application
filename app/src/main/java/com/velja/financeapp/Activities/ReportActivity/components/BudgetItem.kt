package com.velja.financeapp.Activities.ReportActivity.components

import android.icu.text.DecimalFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BudgetItem(
    budget: BudgetDomain,
    index: Int,
    onDelete: (BudgetDomain) -> Unit = {},
    onEdit: (BudgetDomain) -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    var show by remember { mutableStateOf(true) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                show = false
                onDelete(budget)
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
                        .padding(vertical = 4.dp, horizontal = 24.dp)
                        .clip(RoundedCornerShape(16.dp))
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
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .combinedClickable(
                        onClick = { },
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onEdit(budget)
                        }
                    )
            ) {
                val (progress, title, price, percent) = createRefs()

                CircularProgressBar(
                    progress = budget.percent.toFloat(),
                    max = 100f,
                    color = if ((index % 2) == 1) colorResource(R.color.blue) else colorResource(R.color.pink),
                    backgroundColor = colorResource(R.color.lightGrey),
                    stroke = 7.dp,
                    modifier = Modifier
                        .size(70.dp)
                        .constrainAs(progress) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start, margin = 16.dp)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Text(
                    budget.title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(progress.top, margin = 8.dp)
                        start.linkTo(progress.end, margin = 8.dp)
                    }
                )

                Text(
                    "$${DecimalFormat("###,###,###,###").format(budget.price)} /Month",
                    color = colorResource(R.color.grey),
                    modifier = Modifier.constrainAs(price) {
                        top.linkTo(title.bottom)
                        start.linkTo(title.start)
                        bottom.linkTo(progress.bottom)
                    }
                )

                Text(
                    "%${budget.percent}",
                    color = if ((index % 2) == 1) colorResource(R.color.blue) else colorResource(R.color.pink),
                    modifier = Modifier.constrainAs(percent) {
                        centerTo(progress)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun BudgetItemPreview() {
    val budget = BudgetDomain(title = "Food", price = 100.0, percent = 50.0)
    BudgetItem(budget = budget, index = 1)
}