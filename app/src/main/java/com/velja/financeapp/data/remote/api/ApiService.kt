package com.velja.financeapp.data.remote.api

import com.velja.financeapp.data.remote.dto.BudgetDto
import com.velja.financeapp.data.remote.dto.ExpenseDto
import retrofit2.http.*

interface ApiService {

    @GET("expenses")
    suspend fun getAllExpenses(): List<ExpenseDto>

    @GET("expenses/{id}")
    suspend fun getExpenseById(@Path("id") id: Int): ExpenseDto

    @POST("expenses")
    suspend fun createExpense(@Body expense: ExpenseDto): ExpenseDto

    @PUT("expenses/{id}")
    suspend fun updateExpense(@Path("id") id: Int, @Body expense: ExpenseDto): ExpenseDto

    @DELETE("expenses/{id}")
    suspend fun deleteExpense(@Path("id") id: Int)

    @GET("budgets")
    suspend fun getAllBudgets(): List<BudgetDto>

    @GET("budgets/{id}")
    suspend fun getBudgetById(@Path("id") id: Int): BudgetDto

    @POST("budgets")
    suspend fun createBudget(@Body budget: BudgetDto): BudgetDto

    @PUT("budgets/{id}")
    suspend fun updateBudget(@Path("id") id: Int, @Body budget: BudgetDto): BudgetDto

    @DELETE("budgets/{id}")
    suspend fun deleteBudget(@Path("id") id: Int)
}