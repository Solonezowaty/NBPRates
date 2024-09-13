package com.solonezowaty.currencydetails.domain.repositories

import arrow.core.Either
import com.solonezowaty.currencydetails.data.remote.model.CurrencyDetailsData
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails

interface CurrencyDetailsRepository {
    suspend fun getRateDetails(
        tableType: String,
        currencyCode: String,
        startDate: String,
        endDate: String
    ): Either<Throwable, CurrencyDetails>
}