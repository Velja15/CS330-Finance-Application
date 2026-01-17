package com.velja.financeapp.Activities.DashboardActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.velja.financeapp.Activities.DashboardActivity.screens.MainScreen
import com.velja.financeapp.Activities.ReportActivity.ReportActivity
import com.velja.financeapp.ViewModel.MainViewModel
import com.velja.financeapp.ui.theme.FinanceApplicationTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceApplicationTheme {
                MainScreen(expenses = mainViewModel.loadData(), onCardClick = {
                    startActivity(Intent(this, ReportActivity::class.java))
                })
            }
        }
    }
}

