package com.solonezowaty.nbpratesapp.data.remote

import arrow.core.Either
import com.solonezowaty.nbpratesapp.data.NbpRatesApi
import com.solonezowaty.nbpratesapp.domain.NbpRatesTableRepository
import com.solonezowaty.nbpratesapp.domain.model.RateItem
import com.solonezowaty.nbpratesapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NbpRatesTableRepositoryImpl @Inject constructor(
    private val nbpRatesApi: NbpRatesApi
) : NbpRatesTableRepository {
    override suspend fun getCurrentRates(): Either<String?, List<RateItem>> {
        return withContext(Dispatchers.IO) {
            Either.catch {
                val ratesA = async { getCurrentRatesTable(Constants.RATES_LIST_TYPE_A) }
                val ratesB = async { getCurrentRatesTable(Constants.RATES_LIST_TYPE_B) }
                awaitAll(ratesA, ratesB).flatten()
            }.mapLeft { throwable ->
                throwable.message ?: "Error"
            }
        }
    }

    private suspend fun getCurrentRatesTable(tableType: String): List<RateItem> {
        val response = nbpRatesApi.getCurrentRatesTableType(tableType).first()
        return response.rates.map { rate ->
            RateItem(
                tableType = response.table,
                code = rate.code,
                currency = rate.currency.uppercase(),
                midRate = rate.mid.toBigDecimal().toPlainString()
            )
        }
    }
}