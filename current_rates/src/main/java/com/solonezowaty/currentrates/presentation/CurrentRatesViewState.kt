package com.solonezowaty.currentrates.presentation

import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currentrates.domain.model.RateItem

data class CurrentRatesViewState(
    val currentRatesNetworkResponse: NetworkResponse<List<RateItem>> = NetworkResponse.Loading()
)

