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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _expenses = MutableStateFlow<List<ExpenseDomain>>(emptyList())
    val expenses: StateFlow<List<ExpenseDomain>> = _expenses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    init {
        loadExpenses()
        syncWithApi()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllExpenses().collect { expenseList ->
                _expenses.value = expenseList
                _isLoading.value = false
            }
        }
    }

    fun syncWithApi() {
        viewModelScope.launch {
            _isSyncing.value = true
            try {
                repository.syncExpenses()
            } catch (e: Exception) {
            }
            _isSyncing.value = false
        }
    }
    fun addExpense(expense: ExpenseDomain) {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    fun updateExpense(expense: ExpenseDomain) {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    fun deleteExpense(expense: ExpenseDomain) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun getTotalExpenses(): Double {
        return _expenses.value.sumOf { it.price }
    }
}
