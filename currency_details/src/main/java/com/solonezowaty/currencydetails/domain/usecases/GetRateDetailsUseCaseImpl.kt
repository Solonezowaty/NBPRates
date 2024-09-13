package com.solonezowaty.currencydetails.domain.usecases

import arrow.core.Either
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails
import com.solonezowaty.currencydetails.domain.repositories.CurrencyDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetRateDetailsUseCaseImpl @Inject constructor(
    private val currencyDetailsRepository: CurrencyDetailsRepository
) : GetRateDetailsUseCase {

    override suspend fun getRatesDetails(
        tableType: String,
        currencyCode: String,
        startDate: Instant,
        endDate: Instant
    ): Either<Throwable, CurrencyDetails> {
        return withContext(Dispatchers.IO) {
            currencyDetailsRepository.getRateDetails(
                tableType,
                currencyCode,
                startDate = startDate.toStringDate(),
                endDate = endDate.toStringDate()
            )
        }
    }

    private fun Instant.toStringDate() : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val zonedDateTime = this.atZone(ZoneId.systemDefault())
        return formatter.format(zonedDateTime)
    }
}