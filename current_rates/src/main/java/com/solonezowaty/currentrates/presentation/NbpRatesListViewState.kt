package com.solonezowaty.currentrates.presentation

import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currentrates.domain.model.RateItem

data class NbpRatesListViewState(
    val ratesListNetworkResponse: NetworkResponse<List<RateItem>> = NetworkResponse.Loading()
)

