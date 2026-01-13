package com.velja.financeapp.Repository

import com.velja.financeapp.Domain.ExpenseDomain

class MainRepository{
    val items=mutableListOf(
        ExpenseDomain("Restaurant", 573.12, "img1", "17 June 2025 19:15"),
        ExpenseDomain("McDonalds", 77.82,"img2","16 June 2025 13:57"),
        ExpenseDomain("Cinema", 23.47,"img3","16 June 2025 20:45"),
        ExpenseDomain("Resturant", 573.12,"img1","15 June 2025 22:18")

    )
}