package com.solonezowaty.currencydetails.data.remote.api

import arrow.core.Either
import com.solonezowaty.currencydetails.data.remote.model.CurrencyDetailsData
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyDetailsApi {

    @GET("exchangerates/rates/{table}/{code}/{startDate}/{endDate}/")
    suspend fun getCurrencyDetailsFromLastTwoWeeks(
        @Path("table") tableType: String,
        @Path("code") currencyCode: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): Either<Throwable, CurrencyDetailsData>
}