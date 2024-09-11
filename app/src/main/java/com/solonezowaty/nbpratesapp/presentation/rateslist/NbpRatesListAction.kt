package com.solonezowaty.nbpratesapp.presentation.rateslist

sealed interface NbpRatesListAction {
    data object Retry: NbpRatesListAction
    data object LoadNextPage: NbpRatesListAction
}