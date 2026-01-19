package com.velja.financeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BudgetDto(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("percent")
    val percent: Double
)
