package com.solonezowaty.currencydetails.domain.repositories

import arrow.core.Either
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails

interface CurrencyDetailsRepository {
    suspend fun getCurrencyDetails(
        tableType: String,
        currencyCode: String,
        startDate: String,
        endDate: String
    ): Either<Throwable, CurrencyDetails>
}