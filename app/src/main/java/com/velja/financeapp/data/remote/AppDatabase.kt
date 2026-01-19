package com.velja.financeapp.data.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.data.remote.dao.BudgetDao
import com.velja.financeapp.data.remote.dao.ExpenseDao
import com.velja.financeapp.data.remote.entity.BudgetEntity
import com.velja.financeapp.data.remote.entity.ExpenseEntity


@Database(
    entities = [ExpenseEntity::class, BudgetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao
}