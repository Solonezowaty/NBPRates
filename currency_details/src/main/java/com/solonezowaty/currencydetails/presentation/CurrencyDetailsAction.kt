package com.solonezowaty.currencydetails.presentation

sealed interface CurrencyDetailsAction {
    data object Retry: CurrencyDetailsAction
}