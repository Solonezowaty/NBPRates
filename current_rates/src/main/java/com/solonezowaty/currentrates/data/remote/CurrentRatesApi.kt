package com.solonezowaty.currentrates.data.remote

import arrow.core.Either
import com.solonezowaty.currentrates.data.remote.model.RateTablesData
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrentRatesApi {
    /*
        This method require table type which is 'A', 'B' or 'C'.
        For purpose of this app we're passing only 'A' and 'B'
     */
    @GET("exchangerates/tables/{table}/")
    suspend fun getCurrentRateTables(
        @Path("table") tableType: String
    ): Either<Throwable, List<RateTablesData>>
}