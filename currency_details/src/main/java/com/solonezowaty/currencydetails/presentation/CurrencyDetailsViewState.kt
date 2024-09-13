package com.solonezowaty.currencydetails.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class CurrencyDetailsViewState(
    val currencyDetails: com.solonezowaty.core.utils.NetworkResponse<com.solonezowaty.currencydetails.domain.model.CurrencyDetails> = com.solonezowaty.core.utils.NetworkResponse.Loading()
)