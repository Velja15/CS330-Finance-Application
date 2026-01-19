package com.velja.financeapp.Activities.DashboardActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.velja.financeapp.Activities.DashboardActivity.screens.MainScreen
import com.velja.financeapp.Activities.ReportActivity.ReportActivity
import com.velja.financeapp.ViewModel.MainViewModel
import com.velja.financeapp.ui.theme.FinanceApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val expenses by mainViewModel.expenses.collectAsState()

            FinanceApplicationTheme {
                MainScreen(
                    expenses = expenses,
                    onCardClick = {
                        startActivity(Intent(this, ReportActivity::class.java))
                    },
                    onAddExpense = { expense ->
                        mainViewModel.addExpense(expense)
                    }
                )
            }
        }
    }
}

