package com.solonezowaty.currencydetails.domain.usecases

import arrow.core.Either
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails
import java.time.Instant

interface GetRateDetailsUseCase {
    suspend fun getRatesDetails(
        tableType: String,
        currencyCode: String,
        startDate: Instant,
        endDate: Instant
    ): Either<Throwable, CurrencyDetails>
}