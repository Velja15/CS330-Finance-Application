package com.velja.financeapp.Repository

import android.util.Log
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.data.remote.api.ApiService
import com.velja.financeapp.data.remote.dao.BudgetDao
import com.velja.financeapp.data.remote.dao.ExpenseDao
import com.velja.financeapp.data.remote.mapper.toDomain
import com.velja.financeapp.data.remote.mapper.toDto
import com.velja.financeapp.data.remote.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val budgetDao: BudgetDao,
    private val apiService: ApiService
) {

    companion object {
        private const val TAG = "MainRepository"
    }


    fun getAllExpenses(): Flow<List<ExpenseDomain>> =
        expenseDao.getAllExpenses().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun insertExpense(expense: ExpenseDomain) {
        try {
            Log.d(TAG, "Attempting to create expense on API: ${expense.title}")
            val createdExpense = apiService.createExpense(expense.toDto())
            Log.d(TAG, "API Success! Created expense with ID: ${createdExpense.id}")
            expenseDao.insertExpense(createdExpense.toEntity())
        } catch (e: Exception) {
            Log.e(TAG, "API Failed - saving locally only. Error: ${e.message}")
            e.printStackTrace()
            expenseDao.insertExpense(expense.toEntity())
        }
    }

    suspend fun deleteExpense(expense: ExpenseDomain) {
        try {
            Log.d(TAG, "Attempting to delete expense from API: ${expense.id}")
            apiService.deleteExpense(expense.id)
            Log.d(TAG, "API Success! Deleted expense")
        } catch (e: Exception) {
            Log.e(TAG, "API Failed - deleting locally only. Error: ${e.message}")
        }
        expenseDao.deleteExpense(expense.toEntity())
    }

    suspend fun syncExpenses() {
        try {
            Log.d(TAG, "=== SYNCING EXPENSES FROM API ===")
            val remoteExpenses = apiService.getAllExpenses()
            Log.d(TAG, "API Success! Fetched ${remoteExpenses.size} expenses")

            expenseDao.deleteAllExpenses()
            Log.d(TAG, "Cleared local expenses")

            remoteExpenses.forEach { dto ->
                expenseDao.insertExpense(dto.toEntity())
                Log.d(TAG, "Saved expense: ${dto.title}")
            }
            Log.d(TAG, "=== SYNC COMPLETE ===")
        } catch (e: Exception) {
            Log.e(TAG, "=== SYNC FAILED ===")
            Log.e(TAG, "Error: ${e.message}")
            e.printStackTrace()
        }
    }


    fun getAllBudgets(): Flow<List<BudgetDomain>> =
        budgetDao.getAllBudgets().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun insertBudget(budget: BudgetDomain) {
        try {
            Log.d(TAG, "Attempting to create budget on API: ${budget.title}")
            val createdBudget = apiService.createBudget(budget.toDto())
            Log.d(TAG, "API Success! Created budget with ID: ${createdBudget.id}")
            budgetDao.insertBudget(createdBudget.toEntity())
        } catch (e: Exception) {
            Log.e(TAG, "API Failed - saving locally only. Error: ${e.message}")
            e.printStackTrace()
            budgetDao.insertBudget(budget.toEntity())
        }
    }

    suspend fun deleteBudget(budget: BudgetDomain) {
        try {
            Log.d(TAG, "Attempting to delete budget from API: ${budget.id}")
            apiService.deleteBudget(budget.id)
            Log.d(TAG, "API Success! Deleted budget")
        } catch (e: Exception) {
            Log.e(TAG, "API Failed - deleting locally only. Error: ${e.message}")
        }
        budgetDao.deleteBudget(budget.toEntity())
    }

    suspend fun syncBudgets() {
        try {
            Log.d(TAG, "=== SYNCING BUDGETS FROM API ===")
            val remoteBudgets = apiService.getAllBudgets()
            Log.d(TAG, "API Success! Fetched ${remoteBudgets.size} budgets")

            budgetDao.deleteAllBudgets()
            Log.d(TAG, "Cleared local budgets")

            remoteBudgets.forEach { dto ->
                budgetDao.insertBudget(dto.toEntity())
                Log.d(TAG, "Saved budget: ${dto.title}")
            }
            Log.d(TAG, "=== SYNC COMPLETE ===")
        } catch (e: Exception) {
            Log.e(TAG, "=== SYNC FAILED ===")
            Log.e(TAG, "Error: ${e.message}")
            e.printStackTrace()
        }
    }
}