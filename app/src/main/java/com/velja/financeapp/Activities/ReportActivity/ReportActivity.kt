package com.velja.financeapp.Activities.ReportActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.velja.financeapp.Activities.ReportActivity.screens.ReportScreen
import com.velja.financeapp.R
import com.velja.financeapp.ViewModel.MainViewModel
import com.velja.financeapp.ViewModel.ReportViewModel
import com.velja.financeapp.ui.theme.FinanceApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val reportViewModel: ReportViewModel = hiltViewModel()
            val budgets by reportViewModel.budgets.collectAsState()

            FinanceApplicationTheme {
                ReportScreen(
                    budgets = budgets,
                    onBack = { finish() },
                    onAddBudget = { budget ->
                        reportViewModel.addBudget(budget)
                    }
                )
            }
        }
    }
}