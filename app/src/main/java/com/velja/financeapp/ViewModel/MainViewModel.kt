package com.velja.financeapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.Repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _expenses = MutableStateFlow<List<ExpenseDomain>>(emptyList())
    val expenses: StateFlow<List<ExpenseDomain>> get() = _expenses

    private val _budgets = MutableStateFlow<List<BudgetDomain>>(emptyList())
    val budgets: StateFlow<List<BudgetDomain>> get() = _budgets

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _expenses.value = repository.getAllExpenses()
            _budgets.value = repository.getAllBudgets()
        }
    }
}
