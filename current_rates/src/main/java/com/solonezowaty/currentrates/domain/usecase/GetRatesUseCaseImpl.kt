package com.solonezowaty.currentrates.domain.usecase

import arrow.core.Either
import arrow.core.getOrElse
import com.solonezowaty.core.utils.Constants
import com.solonezowaty.currentrates.domain.model.RateItem
import com.solonezowaty.currentrates.domain.repository.RateTablesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRatesUseCaseImpl @Inject constructor(
    private val rateTablesRepository: RateTablesRepository
) : GetRatesUseCase {
    override suspend fun getRates(): Either<Throwable, List<RateItem>> {
        return withContext(Dispatchers.IO) {
            val ratesA = async { rateTablesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) }
            val ratesB = async { rateTablesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) }

            val (resultA, resultB) = awaitAll(ratesA, ratesB)

            when {
                resultA.isLeft() -> resultA
                resultB.isLeft() -> resultB
                else -> {
                    Either.Right(resultA.getOrElse { emptyList() } + resultB.getOrElse { emptyList() })
                }
            }
        }
    }
}