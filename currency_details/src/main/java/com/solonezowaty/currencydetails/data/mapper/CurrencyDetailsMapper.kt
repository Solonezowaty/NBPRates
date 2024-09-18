package com.solonezowaty.currencydetails.data.mapper

import com.solonezowaty.core.utils.Constants
import com.solonezowaty.currencydetails.data.remote.model.CurrencyDetailsData
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails
import com.solonezowaty.currencydetails.domain.model.CurrencyRate

fun CurrencyDetailsData.toCurrencyDetails(currentMidRate: Double) = CurrencyDetails(
    currency = currency,
    code = code,
    ratesList = rates.map { rate ->
        CurrencyRate(
            date = rate.effectiveDate,
            rate = rate.mid,
            isDeviated = rate.mid >= currentMidRate * Constants.IS_DEVIATED_MULTIPLIER
        )
    }
)