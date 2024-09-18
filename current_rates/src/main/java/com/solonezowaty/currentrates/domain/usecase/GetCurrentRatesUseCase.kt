package com.solonezowaty.currentrates.domain.usecase

import arrow.core.Either
import com.solonezowaty.currentrates.domain.model.RateItem

interface GetCurrentRatesUseCase {
    suspend fun getCurrentRates(): Either<Throwable, List<RateItem>>
}