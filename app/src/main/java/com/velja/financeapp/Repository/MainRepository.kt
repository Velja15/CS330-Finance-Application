package com.velja.financeapp.Repository

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

    fun getAllExpenses(): Flow<List<ExpenseDomain>> =
        expenseDao.getAllExpenses().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun insertExpense(expense: ExpenseDomain) {
        try {
            val createdExpense = apiService.createExpense(expense.toDto())
            expenseDao.insertExpense(createdExpense.toEntity())
        } catch (e: Exception) {
            expenseDao.insertExpense(expense.toEntity())
        }
    }

    suspend fun deleteExpense(expense: ExpenseDomain) {
        try {
            apiService.deleteExpense(expense.id)
        } catch (e: Exception) {
        }
        expenseDao.deleteExpense(expense.toEntity())
    }

    suspend fun syncExpenses() {
        try {
            val remoteExpenses = apiService.getAllExpenses()
            expenseDao.deleteAllExpenses()
            remoteExpenses.forEach { dto ->
                expenseDao.insertExpense(dto.toEntity())
            }
        } catch (e: Exception) {
        }
    }

    fun getAllBudgets(): Flow<List<BudgetDomain>> =
        budgetDao.getAllBudgets().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun insertBudget(budget: BudgetDomain) {
        try {
            val createdBudget = apiService.createBudget(budget.toDto())
            budgetDao.insertBudget(createdBudget.toEntity())
        } catch (e: Exception) {
            budgetDao.insertBudget(budget.toEntity())
        }
    }
    suspend fun deleteBudget(budget: BudgetDomain) {
        try {
            apiService.deleteBudget(budget.id)
        } catch (e: Exception) {
        }
        budgetDao.deleteBudget(budget.toEntity())
    }


    suspend fun syncBudgets() {
        try {
            val remoteBudgets = apiService.getAllBudgets()
            budgetDao.deleteAllBudgets()
            remoteBudgets.forEach { dto ->
                budgetDao.insertBudget(dto.toEntity())
            }
        } catch (e: Exception) {
        }
    }
}