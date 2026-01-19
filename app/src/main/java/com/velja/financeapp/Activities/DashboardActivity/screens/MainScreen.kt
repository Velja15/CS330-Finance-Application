package com.velja.financeapp.Activities.DashboardActivity.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.velja.financeapp.Activities.DashboardActivity.components.ActionButtonRow
import com.velja.financeapp.Activities.DashboardActivity.components.AddExpenseDialog
import com.velja.financeapp.Activities.DashboardActivity.components.BottomNavigationBar
import com.velja.financeapp.Activities.DashboardActivity.components.CardSection
import com.velja.financeapp.Activities.DashboardActivity.components.ExpenseItem
import com.velja.financeapp.Activities.DashboardActivity.components.HeaderSection
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.R


@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    val expenses = listOf(
        ExpenseDomain(0,"Restaurant", 573.12, "resturant", "17 June 2025 19:15"),
        ExpenseDomain(1,"McDonalds", 77.82,"mcdonald","16 June 2025 13:57"),
        ExpenseDomain(2,"Cinema", 23.47,"cinema","16 June 2025 20:45"),
        ExpenseDomain(3,"Resturant", 573.12,"resturant","15 June 2025 22:18")
    )
    MainScreen(expenses = expenses)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCardClick:()->Unit = {},
    expenses:List<ExpenseDomain>,
    onAddExpense: (ExpenseDomain) -> Unit = {},
    onRefresh: () -> Unit = {},
    isRefreshing: Boolean = false
){
    var showAddDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { HeaderSection() }
                item { CardSection(onClick = onCardClick) }
                item {
                    ActionButtonRow(
                        onAddClick = { showAddDialog = true }
                    )
                }

                items(expenses) { item ->
                    ExpenseItem(item)
                }
            }
        }

        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(80.dp),
            onItemSelected = { itemId ->
                if (itemId == R.id.wallet) {
                }
            }
        )
    }
    if (showAddDialog) {
        AddExpenseDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { expense ->
                onAddExpense(expense)
                showAddDialog = false
            }
        )
    }
}