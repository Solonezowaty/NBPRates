package com.solonezowaty.currencydetails.presentation

import androidx.compose.runtime.Immutable
import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails

@Immutable
data class CurrencyDetailsViewState(
    val currencyDetailsNetworkResponse: NetworkResponse<CurrencyDetails> = NetworkResponse.Loading()
)