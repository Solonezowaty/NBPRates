package com.solonezowaty.currentrates.domain.repository

import arrow.core.Either
import com.solonezowaty.currentrates.domain.model.RateItem

interface CurrentRatesRepository {
    suspend fun getCurrentRates(tableType: String): Either<Throwable, List<RateItem>>
}