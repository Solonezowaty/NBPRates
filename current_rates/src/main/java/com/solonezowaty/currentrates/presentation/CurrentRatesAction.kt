package com.solonezowaty.currentrates.presentation

sealed interface CurrentRatesAction {
    data object Retry: CurrentRatesAction
    data object LoadNextPage: CurrentRatesAction
}