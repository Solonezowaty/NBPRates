package com.solonezowaty.core

sealed class Destinations(val route: String) {
    data object RatesList : Destinations("rates_list")
    data object CurrencyDetails: Destinations("currency_details/{tableType}/{code}") {
        fun createRoute(tableType: String, code: String) = "currency_details/$tableType/$code"
    }
}