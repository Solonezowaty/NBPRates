package com.solonezowaty.currentrates.domain.usecase

import arrow.core.Either
import com.solonezowaty.currentrates.domain.model.RateItem

interface GetRatesUseCase {
    suspend fun getRates(): Either<Throwable, List<RateItem>>
}