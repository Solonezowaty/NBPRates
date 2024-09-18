package com.solonezowaty.currentrates.data.repository

import arrow.core.Either
import com.solonezowaty.currentrates.data.mapper.toRateItem
import com.solonezowaty.currentrates.data.remote.CurrentRatesApi
import com.solonezowaty.currentrates.domain.repository.CurrentRatesRepository
import com.solonezowaty.currentrates.domain.model.RateItem
import javax.inject.Inject

class CurrentRatesRepositoryImpl @Inject constructor(
    private val currentRatesApi: CurrentRatesApi
) : CurrentRatesRepository {

    override suspend fun getCurrentRates(tableType: String): Either<Throwable, List<RateItem>> {
        return currentRatesApi.getCurrentRatesTable(tableType).fold(
            ifLeft = { error -> Either.Left(error) },
            ifRight = { rates ->
                val ratesList = rates.first()
                val rateItems = ratesList.rates.map { rate ->
                    rate.toRateItem(ratesList.table)
                }
                Either.Right(rateItems)
            }
        )
    }
}