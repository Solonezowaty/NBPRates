package com.solonezowaty.nbpratesapp.data.remote

import arrow.core.Either
import com.solonezowaty.nbpratesapp.data.NbpRatesApi
import com.solonezowaty.nbpratesapp.domain.NbpRatesTableRepository
import com.solonezowaty.nbpratesapp.domain.model.NbpRatesTableData
import javax.inject.Inject

class NbpRatesTableRepositoryImpl @Inject constructor(
    private val nbpRatesApi: NbpRatesApi
): NbpRatesTableRepository {
    override suspend fun getCurrentRatesTable(type: String): Either<String?, NbpRatesTableData> {
        return Either.catch {
            nbpRatesApi.getCurrentRatesTableType(type)
        }.mapLeft { throwable ->
            throwable.message ?: "Error"
        }
    }
}