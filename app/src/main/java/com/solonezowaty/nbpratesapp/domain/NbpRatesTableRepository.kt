package com.solonezowaty.nbpratesapp.domain

import arrow.core.Either
import com.solonezowaty.nbpratesapp.domain.model.NbpRatesTableData

interface NbpRatesTableRepository {
    suspend fun getCurrentRatesTable(type: String): Either<String?, NbpRatesTableData>
}