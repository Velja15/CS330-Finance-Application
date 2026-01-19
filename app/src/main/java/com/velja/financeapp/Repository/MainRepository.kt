package com.velja.financeapp.Repository

import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.data.remote.dao.BudgetDao
import com.velja.financeapp.data.remote.dao.ExpenseDao
import com.velja.financeapp.data.remote.mapper.toDomain
import com.velja.financeapp.data.remote.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val budgetDao: BudgetDao
) {

    fun getAllExpenses(): Flow<List<ExpenseDomain>> =
        expenseDao.getAllExpenses().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun insertExpense(expense: ExpenseDomain) =
        expenseDao.insertExpense(expense.toEntity())

    suspend fun deleteExpense(expense: ExpenseDomain) =
        expenseDao.deleteExpense(expense.toEntity())

    fun getAllBudgets(): Flow<List<BudgetDomain>> =
        budgetDao.getAllBudgets().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun insertBudget(budget: BudgetDomain) =
        budgetDao.insertBudget(budget.toEntity())

    suspend fun deleteBudget(budget: BudgetDomain) =
        budgetDao.deleteBudget(budget.toEntity())
}