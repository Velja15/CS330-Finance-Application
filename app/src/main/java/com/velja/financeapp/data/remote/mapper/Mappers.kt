package com.velja.financeapp.data.remote.mapper

import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.data.remote.entity.BudgetEntity
import com.velja.financeapp.data.remote.entity.ExpenseEntity

fun ExpenseEntity.toDomain(): ExpenseDomain {
    return ExpenseDomain(
        id = id,
        title = title,
        price = price,
        pic = pic,
        time = time
    )
}

fun ExpenseDomain.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        title = title,
        price = price,
        pic = pic,
        time = time
    )
}

fun BudgetEntity.toDomain(): BudgetDomain {
    return BudgetDomain(
        id = id,
        title = title,
        price = price,
        percent = percent
    )
}

fun BudgetDomain.toEntity(): BudgetEntity {
    return BudgetEntity(
        id = id,
        title = title,
        price = price,
        percent = percent
    )
}