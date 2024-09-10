package com.solonezowaty.nbpratesapp.domain

import arrow.core.Either
import com.solonezowaty.nbpratesapp.domain.model.RateItem

interface NbpRatesTableRepository {
    suspend fun getCurrentRates(): Either<String?, List<RateItem>>
}