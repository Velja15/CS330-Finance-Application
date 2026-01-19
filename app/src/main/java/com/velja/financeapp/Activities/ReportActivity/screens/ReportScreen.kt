package com.velja.financeapp.Activities.ReportActivity.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.velja.financeapp.Activities.DashboardActivity.components.BottomNavigationBar
import com.velja.financeapp.Activities.ReportActivity.components.AddBudgetDialog
import com.velja.financeapp.Activities.ReportActivity.components.BudgetItem
import com.velja.financeapp.Activities.ReportActivity.components.CenterStatsCard
import com.velja.financeapp.Activities.ReportActivity.components.GradientHeader
import com.velja.financeapp.Activities.ReportActivity.components.SummaryColumns
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.R

@Composable
fun ReportScreen(
    budgets:List<BudgetDomain>,
    onBack:()->Unit,
    onAddBudget: (BudgetDomain) -> Unit = {}
) {
    var showAddDialog by remember { mutableStateOf(false)}
    ConstraintLayout (modifier = Modifier.fillMaxSize()){
        val (scrollRef,bottomNavRef)=createRefs()

        ReportContent(
            budgets = budgets,
            modifier = Modifier
                .constrainAs(scrollRef){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomNavRef.top)

                },
            onBack = onBack,
            onAddBudget = { showAddDialog = true}
        )
        BottomNavigationBar(
            modifier = Modifier
                .height(80.dp)
                .constrainAs(bottomNavRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onItemSelected = {itemId->
                if(itemId==R.id.wallet) {
                    onBack()
                }
            }
        )
    }

    if (showAddDialog) {
        AddBudgetDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { budget ->
                onAddBudget(budget)
                showAddDialog = false
            }
        )
    }

}

@Composable
fun ReportContent(
    budgets: List<BudgetDomain>,
    modifier: Modifier= Modifier,
    onBack: () -> Unit,
    onAddBudget: () -> Unit = {}
){
    LazyColumn(
        modifier=modifier
            .background(Color.White),

    ){
        item {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)

            ) {
                val (header, card) = createRefs()
                GradientHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .constrainAs(header){
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    onBack = onBack
                )
                CenterStatsCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 24.dp)
                        .constrainAs(card) {
                            top.linkTo(header.bottom)
                            bottom.linkTo(header.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
        item {
            SummaryColumns(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(colorResource(R.color.lightBlue),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(8.dp)
            )
        }

        item {
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(top=16.dp)
                .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    "My budget",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = colorResource(R.color.darkBlue)
                )

                Text(
                    "Add",
                    color = colorResource(R.color.blue),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onAddBudget() }
                )
            }
        }

        itemsIndexed(budgets){index,item ->
            BudgetItem(budget = item, index = index)
        }
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    val budgets = listOf(
        BudgetDomain(title = "Groceries", price = 100.0, percent = 20.0),
        BudgetDomain(title = "Rent", price = 500.0, percent = 50.0),
        BudgetDomain(title = "Entertainment", price = 50.0, percent = 10.0),
        )
    ReportScreen(
        budgets = budgets,
        onBack = {}
    )
}