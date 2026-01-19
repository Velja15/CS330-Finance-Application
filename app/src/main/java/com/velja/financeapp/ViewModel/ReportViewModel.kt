package com.velja.financeapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _budgets = MutableStateFlow<List<BudgetDomain>>(emptyList())
    val budgets: StateFlow<List<BudgetDomain>> = _budgets.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadBudgets()
    }

    private fun loadBudgets() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllBudgets().collect { budgetList ->
                _budgets.value = budgetList
                _isLoading.value = false
            }
        }
    }

    fun addBudget(budget: BudgetDomain) {
        viewModelScope.launch {
            repository.insertBudget(budget)
        }
    }

    fun deleteBudget(budget: BudgetDomain) {
        viewModelScope.launch {
            repository.deleteBudget(budget)
        }
    }

    fun getTotalBudget(): Double {
        return _budgets.value.sumOf { it.price }
    }

    fun getAveragePercent(): Double {
        val budgets = _budgets.value
        return if (budgets.isEmpty()) 0.0
        else budgets.sumOf { it.percent } / budgets.size
    }
}