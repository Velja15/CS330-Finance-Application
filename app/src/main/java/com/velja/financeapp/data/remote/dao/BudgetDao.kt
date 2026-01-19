package com.velja.financeapp.data.remote.dao

import androidx.room.*
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.data.remote.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity)

    @Query("SELECT * FROM budgets ORDER BY id DESC")
    fun getAllBudgets(): Flow<List<BudgetEntity>>

    @Delete
    suspend fun deleteBudget(budget: BudgetEntity)

    @Query("DELETE FROM budgets")
    suspend fun deleteAllBudgets()
}