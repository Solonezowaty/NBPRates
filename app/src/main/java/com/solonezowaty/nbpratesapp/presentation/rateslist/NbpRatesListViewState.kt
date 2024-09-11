package com.solonezowaty.nbpratesapp.presentation.rateslist

import com.solonezowaty.nbpratesapp.domain.model.RateItem
import com.solonezowaty.nbpratesapp.utils.NetworkResponse

data class NbpRatesListViewState(
    val ratesListNetworkResponse: NetworkResponse<List<RateItem>> = NetworkResponse.Loading()
)

