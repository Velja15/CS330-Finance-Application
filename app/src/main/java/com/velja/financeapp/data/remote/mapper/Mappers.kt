package com.velja.financeapp.data.remote.mapper

import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.data.remote.dto.BudgetDto
import com.velja.financeapp.data.remote.dto.ExpenseDto
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

fun ExpenseDto.toDomain(): ExpenseDomain {
    return ExpenseDomain(
        id = id,
        title = title,
        price = price,
        pic = pic,
        time = time
    )
}

fun ExpenseDomain.toDto(): ExpenseDto {
    return ExpenseDto(
        id = id,
        title = title,
        price = price,
        pic = pic,
        time = time
    )
}

// DTO ↔ Entity
fun ExpenseDto.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        title = title,
        price = price,
        pic = pic,
        time = time
    )
}

fun ExpenseEntity.toDto(): ExpenseDto {
    return ExpenseDto(
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

fun BudgetDto.toDomain(): BudgetDomain {
    return BudgetDomain(
        id = id,
        title = title,
        price = price,
        percent = percent
    )
}

fun BudgetDomain.toDto(): BudgetDto {
    return BudgetDto(
        id = id,
        title = title,
        price = price,
        percent = percent
    )
}

fun BudgetDto.toEntity(): BudgetEntity {
    return BudgetEntity(
        id = id,
        title = title,
        price = price,
        percent = percent
    )
}

fun BudgetEntity.toDto(): BudgetDto {
    return BudgetDto(
        id = id,
        title = title,
        price = price,
        percent = percent
    )
}