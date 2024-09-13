package com.solonezowaty.currencydetails.domain.model

data class CurrencyDetails(
    val currency: String,
    val code: String,
    val ratesList: List<CurrencyRate>
)

data class CurrencyRate(
    val date: String,
    val rate: Double,
    val isDeviated: Boolean
)