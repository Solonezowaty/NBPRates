package com.solonezowaty.currentrates.presentation

sealed interface NbpRatesListAction {
    data object Retry: NbpRatesListAction
    data object LoadNextPage: NbpRatesListAction
}