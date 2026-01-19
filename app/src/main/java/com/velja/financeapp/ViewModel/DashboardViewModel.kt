package com.velja.financeapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.Repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseDomain>>(emptyList())
    val expenses: StateFlow<List<ExpenseDomain>> = _expenses.asStateFlow()

    private val _budgets = MutableStateFlow<List<BudgetDomain>>(emptyList())
    val budgets: StateFlow<List<BudgetDomain>> = _budgets.asStateFlow()

    private val _dashboardStats = MutableStateFlow(DashboardStats())
    val dashboardStats: StateFlow<DashboardStats> = _dashboardStats.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            combine(
                repository.getAllExpenses(),
                repository.getAllBudgets()
            ) { expenses, budgets ->
                _expenses.value = expenses
                _budgets.value = budgets

                _dashboardStats.value = DashboardStats(
                    totalExpenses = expenses.sumOf { it.price },
                    totalBudget = budgets.sumOf { it.price },
                    expenseCount = expenses.size,
                    budgetCount = budgets.size
                )
            }.collect {}
        }
    }
}

data class DashboardStats(
    val totalExpenses: Double = 0.0,
    val totalBudget: Double = 0.0,
    val expenseCount: Int = 0,
    val budgetCount: Int = 0
) {
    val remainingBudget: Double
        get() = totalBudget - totalExpenses
}