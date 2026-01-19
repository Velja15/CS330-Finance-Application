package com.velja.financeapp.Domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "expenses")
data class ExpenseDomain(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title:String="",
    val price: Double=0.0,
    val pic: String = "",
    val time: String=""
): Serializable
