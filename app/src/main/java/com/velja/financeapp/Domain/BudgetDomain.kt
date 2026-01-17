package com.velja.financeapp.Domain

import java.io.Serializable

data class BudgetDomain(
    val title:String,
    val price:Double =0.0,
    val percent: Double =0.0
): Serializable
