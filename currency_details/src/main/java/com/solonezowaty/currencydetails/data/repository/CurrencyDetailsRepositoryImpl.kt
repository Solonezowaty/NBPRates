package com.solonezowaty.currencydetails.data.repository

import arrow.core.Either
import com.solonezowaty.currencydetails.data.mapper.toCurrencyDetails
import com.solonezowaty.currencydetails.data.remote.api.CurrencyDetailsApi
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails
import com.solonezowaty.currencydetails.domain.repositories.CurrencyDetailsRepository
import javax.inject.Inject

class CurrencyDetailsRepositoryImpl @Inject constructor(
    private val nbpRatesApi: CurrencyDetailsApi
) : CurrencyDetailsRepository {

    override suspend fun getRateDetails(
        tableType: String,
        currencyCode: String,
        startDate: String,
        endDate: String
    ): Either<Throwable, CurrencyDetails> {
        return nbpRatesApi.getRateDetailsFromLastTwoWeeks(
            tableType = tableType,
            currencyCode = currencyCode,
            startDate = startDate,
            endDate = endDate,
        ).fold(
            ifLeft = { error -> Either.Left(error) },
            ifRight = { details ->
                val sortedRates = details.rates.sortedByDescending { it.effectiveDate }
                val currentRate = sortedRates.first().mid
                Either.Right(details.copy(rates = sortedRates).toCurrencyDetails(currentRate))
            }
        )
    }
}