package com.velja.financeapp.Repository

import com.velja.financeapp.Domain.ExpenseDomain

class MainRepository{
    val items=mutableListOf(
        ExpenseDomain("Restaurant", 573.12, "resturant", "17 June 2025 19:15"),
        ExpenseDomain("McDonalds", 77.82,"mcdonald","16 June 2025 13:57"),
        ExpenseDomain("Cinema", 23.47,"cinema","16 June 2025 20:45"),
        ExpenseDomain("Restaurant", 573.12,"resturant","15 June 2025 22:18")

    )
}